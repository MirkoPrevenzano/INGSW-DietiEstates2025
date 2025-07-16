import { Component, HostListener, OnInit } from '@angular/core';
import { AgChartOptions } from 'ag-charts-community';
import { AgentService } from '../../rest-backend/agent/agent.service';
import { CommonModule } from '@angular/common';
import { EstateStatsComponent } from './estate-stats/estate-stats.component';
import { GeneralStatsComponent } from './general-stats/general-stats.component';
import { AgChartsComponent } from '../../componentCustom/ag-charts/ag-charts.component';
import { DownloadFileService } from '../../_service/download-file/download-file.service';
import { ChartsConfigService } from '../../_service/charts-config/charts-config.service';
import { AgentGeneralStats } from '../../model/agentGeneralStats';
import { ButtonCustomComponent } from '../../componentCustom/button-custom/button-custom.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-agent-dashboard',
  imports: [
    AgChartsComponent,
    CommonModule,
    EstateStatsComponent,
    GeneralStatsComponent,
    ButtonCustomComponent
  ],
  templateUrl: './agent-dashboard.component.html',
  styleUrls: ['./agent-dashboard.component.scss']
})
export class AgentDashboardComponent implements OnInit {
  estatesSoldRentedPerMonth: number[] = [0,0,0,0,0,0,0,0,0,0,0,0]
  monthlySalesChartOptions: AgChartOptions = {}
  successRatePieChartOptions: AgChartOptions = {}
  listingTypePieChartOptions: AgChartOptions = {}
  dropdownOpen = false 
  tableData = []
  generalStats: AgentGeneralStats = {
    totalUploadedRealEstates: 0,
    totalRentedRealEstates: 0,
    totalSoldRealEstates: 0,
    totalIncome: 0.0,
    rentalsIncome: 0.0,
    salesIncome: 0.0,
    successRate: 0.0,
    salesToRentalsRatio:0.0,
    rentalToSalesRatio: 0.0,
 }





  constructor(
    private readonly agentService: AgentService,
    private readonly downloadFileService: DownloadFileService,
    private readonly chartsConfig: ChartsConfigService,
    private readonly notifyService: ToastrService
  ){}

  ngOnInit() {
    const user= localStorage.getItem('user')
     if(user)
      this.agentService.agentStats(user).subscribe({
        next: (response:{agentStats:AgentGeneralStats, estatesPerMonths:number[]})=>{
          if(response){
            this.generalStats = response.agentStats
            this.estatesSoldRentedPerMonth = response.estatesPerMonths
            this.createMonthlySalesChart()
            this.createPieCharts()
          }
        },
        error: (err) => {
          if(err?.error.status >= 400 && err?.error.status < 500)
            this.notifyService.warning(err?.error.description)
          if(err?.error.status >= 500 && err?.error.status < 600)
            this.notifyService.error(err?.error.description)
        }
      })    
  }


 

  createPieCharts() {
    this.successRatePieChartOptions = this.chartsConfig.successRateConfig(
      [
        { asset: "Pending", amount: 100- this.generalStats.successRate },
        { asset: "Completed", amount:this.generalStats.successRate},
      ]
    )
    
    this.listingTypePieChartOptions = this.chartsConfig.closeListingConfig(
      [
        { asset: "For Sale", amount: this.generalStats.totalSoldRealEstates },
        { asset: "For Rent", amount: this.generalStats.totalRentedRealEstates },
      ]
    )
  }

  createMonthlySalesChart() {
    this.monthlySalesChartOptions= this.chartsConfig.generateOptionsMontlySalesChart(this.estatesSoldRentedPerMonth);  
  }


  @HostListener('window:resize', ['$event'])
  onResize() {
    this.createMonthlySalesChart()
  }
  
  
  toggleDropdown(){
    this.dropdownOpen = !this.dropdownOpen
  }


  exportPDF(){
    this.agentService.exportPDF().subscribe({
      next: (blob) =>{
        this.downloadFileService.downloadFile(blob, `dashboard_${localStorage.getItem('user')}_${this.getActualDate()}.pdf`)
      },
      error: (err) => {
        if(err?.error.status >= 400 && err?.error.status < 500)
          this.notifyService.warning(err?.error.description)
        if(err?.error.status >= 500 && err?.error.status < 600)
          this.notifyService.error(err?.error.description)
      }
    })
  }


  exportCSV(){
    this.agentService.exportCSV().subscribe({
      next: (blob) => {
        this.downloadFileService.downloadFile(blob, `dashboard_${localStorage.getItem('user')}_${this.getActualDate()}.csv`)
      },
      error: (err) => {
        if(err?.error.status >= 400 && err?.error.status < 500)
          this.notifyService.warning(err?.error.description)
        if(err?.error.status >= 500 && err?.error.status < 600)
          this.notifyService.error(err?.error.description)
      }
    });
     
  }

  getActualDate(){
    const date = new Date();
    const formattedDate = `${date.getDate()}-${date.getMonth() + 1}-${date.getFullYear()}`;
    return formattedDate
  }


   
  

}