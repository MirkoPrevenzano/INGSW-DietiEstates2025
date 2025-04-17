import type { AgCartesianChartOptions, AgSparklineOptions } from 'ag-charts-types';
export declare function sparklineDataPreset(data: any[] | undefined): {
    data: any[] | undefined;
    series?: {
        xKey: string;
        yKey: string;
    }[];
    datumKey?: string;
};
export declare function sparkline(opts: AgSparklineOptions): AgCartesianChartOptions;
