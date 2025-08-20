import { Component, HostListener, OnInit } from '@angular/core';
import { AgChartOptions } from 'ag-charts-community';
import { AgentService } from '../../rest-backend/agent/agent.service';
import { CommonModule } from '@angular/common';
import { EstateStatsComponent } from './estate-stats/estate-stats.component';
import { GeneralStatsComponent } from './general-stats/general-stats.component';
import { AgChartsComponent } from '../../componentCustom/ag-charts/ag-charts.component';
import { DownloadFileService } from '../../_service/download-file/download-file.service';
import { ChartsConfigService } from '../../_service/charts-config/charts-config.service';
import { AgentStats } from '../../model/response/support/agentStats';
import { ButtonCustomComponent } from '../../componentCustom/button-custom/button-custom.component';
import { AgentDashboardPersonalStats } from '../../model/response/agentDashboardPersonalStats';
import { HandleNotifyService } from '../../_service/handle-notify.service';

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
  generalStats: AgentStats = {
    totalUploadedRealEstates: 0,
    totalRentedRealEstates: 0,
    totalSoldRealEstates: 0,
    rentalsIncome: 0.0,
    salesIncome: 0.0
  }





  constructor(
    private readonly agentService: AgentService,
    private readonly downloadFileService: DownloadFileService,
    private readonly chartsConfig: ChartsConfigService,
    private readonly handleError: HandleNotifyService
  ){}

  ngOnInit() {
    const user= localStorage.getItem('user')
     if(user)
      this.agentService.agentStats().subscribe({
        next: (response:AgentDashboardPersonalStats)=>{
          if(response){
            this.generalStats = response.agentStatsDto
            this.estatesSoldRentedPerMonth = response.monthlyDeals
            this.createMonthlySalesChart()
            this.createPieCharts()
          }
        },
        error: (err) => {
          this.handleError.showMessageError(err.error)
        }
      })    
  }


 

  createPieCharts() {
    let pending= this.generalStats.totalUploadedRealEstates-(this.generalStats.totalRentedRealEstates+this.generalStats.totalSoldRealEstates)
    let completed = this.generalStats.totalRentedRealEstates+this.generalStats.totalSoldRealEstates
    this.successRatePieChartOptions = this.chartsConfig.successRateConfig(
      [
        { asset: "Pending", amount: pending },
        { asset: "Completed", amount:completed},
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
        this.handleError.showMessageError(err.error)
      }
    })
  }


  exportCSV(){
    this.agentService.exportCSV().subscribe({
      next: (blob) => {
        this.downloadFileService.downloadFile(blob, `dashboard_${localStorage.getItem('user')}_${this.getActualDate()}.csv`)
      },
      error: (err) => {
        this.handleError.showMessageError(err.error)
      }
    });
     
  }

  getActualDate(){
    const date = new Date();
    const formattedDate = `${date.getDate()}-${date.getMonth() + 1}-${date.getFullYear()}`;
    return formattedDate
  }


   
  

}