import { Injectable } from '@angular/core';
import { AgChartOptions, AgPieSeriesOptions } from 'ag-charts-community';
import { ChartOptionsBar } from '../../model/chartOptions';

@Injectable({
  providedIn: 'root'
})
export class GenerateChartService {

  constructor() { }

  generateChartBar(
    options: ChartOptionsBar,
    fillColor: string = '#2C3E50',
    strokeColor: string = '#2C3E50',
    highlightFillColor: string = '#A9A9A9',
    highlightStrokeColor: string = '#A9A9A9'
  ): AgChartOptions {
    return {
      width: options.chartWidth,
      height: options.chartHeight,
      //container: options.containerElement,
      title: {
        text: options.chartTitle,
      },
      data: options.data,
      series: [
        {
          type: 'bar',
          xKey: options.xKey,
          yKey: options.yKey,
          fill: fillColor,
          stroke: strokeColor,
          highlightStyle: {
            item: {
              fill: highlightFillColor,
              stroke: highlightStrokeColor,
            },
          },
        },
      ],
      axes: [
        {
          type: 'category',
          position: 'bottom',
          title: {
            text: options.xAxisTitle,
          },
          label: { rotation: 0 },
        },
        {
          type: 'number',
          position: 'left',
          title: {
            text: options.yAxisTitle,
          },
        },
      ],
    };
  }


  generateChartPie(
    data: any[],
    title: string,
    angleKey: string,
    labelKey: string,
    firstColor: string ="#1f77b4",
    secondColor: string ="#ff7f0e"
  ): AgChartOptions {
    return {
      width:400,
      height:250,
      data: data,
      title: {
        text: title,
      },
      series: [
        {
          type: 'pie',
          angleKey: angleKey,
          legendItemKey: labelKey,
          label: {
            enabled: true,
            formatter: ({ datum }: { datum: any }) => `${datum[labelKey]}: ${datum[angleKey]}`,
          },
          callout: {
            strokeWidth: 2,
          },
          fills: [firstColor, secondColor, '#2ca02c', '#d62728', '#9467bd', '#8c564b', '#e377c2', '#7f7f7f', '#bcbd22', '#17becf'],
          strokes: [firstColor, secondColor, '#2ca02c', '#d62728', '#9467bd', '#8c564b', '#e377c2', '#7f7f7f', '#bcbd22', '#17becf'],
          
        } as AgPieSeriesOptions,
      ],
      legend: {
        position: 'right',
        enabled:true
      },
    };
  }
}