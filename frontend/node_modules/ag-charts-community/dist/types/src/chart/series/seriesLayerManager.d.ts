import { Group } from '../../scene/group';
import type { SeriesGrouping } from './seriesStateManager';
interface SeriesConfig {
    internalId: string;
    seriesGrouping?: SeriesGrouping;
    contentGroup: Group;
    renderToOffscreenCanvas(): boolean;
    type: string;
}
export declare class SeriesLayerManager {
    private readonly seriesRoot;
    private readonly groups;
    private readonly series;
    private expectedSeriesCount;
    private mode;
    constructor(seriesRoot: Group);
    setSeriesCount(count: number): void;
    requestGroup(seriesConfig: SeriesConfig): Group<any>;
    changeGroup(seriesConfig: SeriesConfig & {
        oldGrouping?: SeriesGrouping;
    }): void;
    releaseGroup(seriesConfig: {
        internalId: string;
        seriesGrouping?: SeriesGrouping;
        contentGroup: Group;
        type: string;
    }): void;
    updateLayerCompositing(): void;
    private lookupIdx;
    destroy(): void;
    private getLowestSeriesZIndex;
}
export {};
