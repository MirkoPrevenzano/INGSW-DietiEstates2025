import type { InternalAgColorType } from 'ag-charts-core';
import type { AgColorType, AgSeriesMarkerStyle } from 'ag-charts-types';
export interface LegendMarker extends Omit<AgSeriesMarkerStyle, 'stroke'> {
    fill?: InternalAgColorType;
    stroke?: AgColorType;
    enabled?: boolean;
}
export interface LegendLine {
    stroke: string;
    strokeOpacity: number;
    strokeWidth: number;
    lineDash: number[];
}
export interface LegendSymbolOptions {
    marker: LegendMarker;
    line?: LegendLine;
}
export declare function legendSymbolSvg(symbol: LegendSymbolOptions, size: number, lineSize?: number): string | undefined;
