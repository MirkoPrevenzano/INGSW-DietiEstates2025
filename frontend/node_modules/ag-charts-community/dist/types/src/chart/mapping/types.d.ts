import type { AgCartesianChartOptions, AgCartesianSeriesOptions, AgChartOptions, AgFlowProportionChartOptions, AgFlowProportionSeriesOptions, AgGaugeChartOptions, AgGaugeOptions, AgHierarchyChartOptions, AgHierarchySeriesOptions, AgPolarChartOptions, AgPolarSeriesOptions, AgStandaloneChartOptions, AgStandaloneSeriesOptions, AgTopologyChartOptions, AgTopologySeriesOptions } from 'ag-charts-types';
export type SeriesOptionsTypes = AgCartesianSeriesOptions | AgPolarSeriesOptions | AgHierarchySeriesOptions | AgTopologySeriesOptions | AgFlowProportionSeriesOptions | AgStandaloneSeriesOptions | AgGaugeOptions;
export type SeriesType = SeriesOptionsTypes['type'];
export declare function optionsType(input: {
    series?: {
        type?: SeriesType;
    }[];
}): NonNullable<SeriesType>;
export declare function isAgCartesianChartOptions(input: AgChartOptions): input is AgCartesianChartOptions;
export declare function isAgPolarChartOptions(input: AgChartOptions): input is AgPolarChartOptions;
export declare function isAgHierarchyChartOptions(input: AgChartOptions): input is AgHierarchyChartOptions;
export declare function isAgTopologyChartOptions(input: AgChartOptions): input is AgTopologyChartOptions;
export declare function isAgFlowProportionChartOptions(input: AgChartOptions): input is AgFlowProportionChartOptions;
export declare function isAgStandaloneChartOptions(input: AgChartOptions): input is AgStandaloneChartOptions;
export declare function isAgGaugeChartOptions(input: any): input is AgGaugeChartOptions;
export declare function isAgPolarChartOptionsWithSeriesBasedLegend(input: AgChartOptions): input is AgPolarChartOptions;
export declare function isSeriesOptionType(input?: string): input is NonNullable<SeriesType>;
