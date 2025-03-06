import { Injectable } from '@angular/core';
import { GenerateChartService } from '../generate-chart/generate-chart.service';
import { AgChartOptions } from 'ag-charts-community';

@Injectable({
  providedIn: 'root'
})
export class ChartsConfigService {

  constructor(
    private readonly chartService: GenerateChartService
  ) { }

  
  generateOptionsMontlySalesChart(estateStatsSellRent:number[]): AgChartOptions {
      const data = estateStatsSellRent.map((sold, index) => ({
        month: new Date(0, index).toLocaleString('en-US', { month: 'long' }),
        sold
      }));
  
      return this.chartService.generateChartBar({
        data: data,
        xKey: 'month',
        yKey: 'sold',
        chartTitle: 'Estates Rent/Sell',
        xAxisTitle: 'Month',
        yAxisTitle: 'Estates Number',
        chartWidth: this.getWidth(),
        chartHeight: 400
      })
  
    }



    successRateConfig(data:any): AgChartOptions {
        
        return this.chartService.generateChartPie(
          data,
          "Success Rate",
          "amount",
          "asset",
          "#7f7f7f"
    
        )
    }

    closeListingConfig(data:any): AgChartOptions {
        
        
        return this.chartService.generateChartPie(
           data,
          "Renting/Selling",
          "amount",
          "asset",
          
    
        )
    }


    getWidth(): number {
      if (window.innerWidth < 900) 
        return 440;
      else 
        return 960;
    }
  
}
