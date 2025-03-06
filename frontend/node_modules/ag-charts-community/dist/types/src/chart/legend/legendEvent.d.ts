import type { AgChartLegendClickEvent, AgChartLegendDoubleClickEvent } from 'ag-charts-types';
type LegendEventState<T extends AgChartLegendClickEvent | AgChartLegendDoubleClickEvent> = {
    apiEvent: T;
    defaultPrevented: boolean;
};
export declare function makeLegendItemEvent<T extends 'click'>(type: T, itemId: string, seriesId: string, event: Event): LegendEventState<AgChartLegendClickEvent>;
export declare function makeLegendItemEvent<T extends 'dblclick'>(type: T, itemId: string, seriesId: string, event: Event): LegendEventState<AgChartLegendDoubleClickEvent>;
export {};
