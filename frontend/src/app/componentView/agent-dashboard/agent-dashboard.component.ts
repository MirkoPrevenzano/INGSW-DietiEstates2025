import { AfterViewInit, Component, HostListener, OnInit } from '@angular/core';
import { AgChartOptions } from 'ag-charts-community';
import { AgentService } from '../../_service/rest-backend/agent/agent.service';
import { CommonModule } from '@angular/common';
import { EstateStatsComponent } from './estate-stats/estate-stats.component';
import { GeneralStatsComponent } from './general-stats/general-stats.component';
import { AgChartsComponent } from '../ag-charts/ag-charts.component';
import { DownloadFileService } from '../../_service/download-file/download-file.service';
import { ChartsConfigService } from '../../_service/charts-config/charts-config.service';
import { AgentGeneralStats } from '../../model/agentGeneralStats';

@Component({
  selector: 'app-agent-dashboard',
  imports: [
    AgChartsComponent,
    CommonModule,
    EstateStatsComponent,
    GeneralStatsComponent
  ],
  templateUrl: './agent-dashboard.component.html',
  styleUrls: ['./agent-dashboard.component.scss']
})
export class AgentDashboardComponent implements OnInit, AfterViewInit {
  estateStatsSellRent: number[] = [0,0,0,0,0,0,0,0,0,0,0,0]
  monthlySalesChartOptions: AgChartOptions = {}
  pieChartOptions: AgChartOptions = {}
  pieChartOptions2: AgChartOptions= {}
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
    private readonly chartsConfig: ChartsConfigService
  ){}

  ngOnInit() {
    const user= localStorage.getItem('user')
     if(user)
      this.agentService.agentStats(user).subscribe((response:{realEstateAgentStats:AgentGeneralStats, estatesPerMonths:number[]})=>{
        if(response){
          this.generalStats = response.realEstateAgentStats
          console.log(response.estatesPerMonths)
          this.estateStatsSellRent = response.estatesPerMonths
          console.log(this.estateStatsSellRent)
          this.createMonthlySalesChart()
          this.createPieCharts()



        }
      })
       
  }


  ngAfterViewInit(): void {
  }

  createPieCharts() {
    this.pieChartOptions = this.chartsConfig.successRateConfig(
      [
        { asset: "Pending", amount: 100- this.generalStats.successRate },
        { asset: "Completed", amount:this.generalStats.successRate},
      ]
    )
    
    this.pieChartOptions2 = this.chartsConfig.closeListingConfig(
      [
        { asset: "For Sale", amount: this.generalStats.totalSoldRealEstates },
        { asset: "For Rent", amount: this.generalStats.totalRentedRealEstates },
      ]
    )
  }

  createMonthlySalesChart() {
    this.monthlySalesChartOptions= this.chartsConfig.generateOptionsMontlySalesChart(this.estateStatsSellRent);  
  }


  @HostListener('window:resize', ['$event'])
  onResize() {
    this.createMonthlySalesChart()
  }
  
  
  toggleDropdown(){
    this.dropdownOpen = !this.dropdownOpen
  }


  exportPDF(){
    this.agentService.exportPDF().subscribe(blob =>{
      this.downloadFileService.downloadFile(blob, `dashboard_${localStorage.getItem('user')}:${this.getActualDate()}.pdf`)
    })
  }


  exportCSV(){
    this.agentService.exportCSV().subscribe(blob =>{
      this.downloadFileService.downloadFile(blob, `dashboard_${localStorage.getItem('user')}_${this.getActualDate()}.csv`)
    })  
  }

  getActualDate(){
    const date = new Date();
    const formattedDate = `${date.getDate()}-${date.getMonth() + 1}-${date.getFullYear()}`;
    return formattedDate
  }


   
  

}