import type { AgInitialStateLegendOptions } from 'ag-charts-types';
import type { MementoOriginator } from '../../api/state/memento';
import { BaseManager } from '../../util/baseManager';
import type { CategoryLegendDatum } from './legendDatum';
export interface LegendChangeEvent {
    type: 'legend-change';
    legendData?: CategoryLegendDatum[];
}
type LegendDataMemento = AgInitialStateLegendOptions[];
export declare class LegendManager extends BaseManager<LegendChangeEvent['type'], LegendChangeEvent> implements MementoOriginator<LegendDataMemento> {
    mementoOriginatorKey: "legend";
    private readonly legendDataMap;
    createMemento(): {
        visible: boolean;
        seriesId: string;
        itemId: any;
        legendItemName: string | undefined;
    }[];
    guardMemento(blob: unknown): blob is LegendDataMemento | undefined;
    restoreMemento(_version: string, _mementoVersion: string, memento: LegendDataMemento | undefined): void;
    private getRestoredData;
    private warnFixed;
    update(data?: CategoryLegendDatum[]): void;
    updateData(seriesId: string, data?: CategoryLegendDatum[]): void;
    clearData(): void;
    toggleItem(enabled: boolean, seriesId: string, itemId?: any, legendItemName?: string): void;
    getData(seriesId?: string): CategoryLegendDatum[];
    getDatum({ seriesId, itemId }?: {
        seriesId?: string;
        itemId?: any;
    }): CategoryLegendDatum | undefined;
    getSeriesEnabled(seriesId: string): boolean | undefined;
    getItemEnabled({ seriesId, itemId }?: {
        seriesId?: string;
        itemId?: any;
    }): boolean;
}
export {};
