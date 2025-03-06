import type { AgInitialStateChartType } from 'ag-charts-types';
import type { ChartService } from '../../chart/chartService';
import type { MementoOriginator } from '../state/memento';
type ChartTypeMemento = AgInitialStateChartType;
export declare class ChartTypeOriginator implements MementoOriginator<ChartTypeMemento> {
    private readonly chartService;
    mementoOriginatorKey: "chartType";
    constructor(chartService: ChartService);
    createMemento(): import("ag-charts-types").AgPriceVolumeChartType;
    guardMemento(blob: unknown): blob is ChartTypeMemento | undefined;
    restoreMemento(_version: string, _mementoVersion: string, memento: ChartTypeMemento | undefined): void;
}
export {};
