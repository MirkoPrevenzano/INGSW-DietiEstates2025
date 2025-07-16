import { CommonModule } from "@angular/common";
import { Component, Input } from "@angular/core";
import { AgChartsModule } from "ag-charts-angular";
import { AgChartOptions } from "ag-charts-community";

@Component({
  selector: 'app-ag-charts',
  imports: [AgChartsModule, CommonModule],
  templateUrl: './ag-charts.component.html',
  styleUrl: './ag-charts.component.scss'
})
export class AgChartsComponent {
  @Input()  chartOptions!:AgChartOptions 

  constructor(){
  }
}
