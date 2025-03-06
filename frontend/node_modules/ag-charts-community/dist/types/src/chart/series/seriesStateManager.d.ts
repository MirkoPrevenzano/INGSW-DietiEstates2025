export type SeriesGrouping = {
    groupIndex: number;
    groupCount: number;
    stackIndex: number;
    stackCount: number;
};
type SeriesGroupingResult = {
    visibleGroupCount: number;
    visibleSameStackCount: number;
    index: number;
};
type SeriesIdLike = {
    internalId: string;
    type: string;
};
type SeriesLike = SeriesIdLike & {
    seriesGrouping?: SeriesGrouping;
    visible: boolean;
};
export declare class SeriesStateManager {
    private readonly groups;
    registerSeries({ internalId, seriesGrouping, visible, type }: SeriesLike): void;
    updateSeries({ internalId, seriesGrouping, visible, type }: SeriesLike): void;
    deregisterSeries({ internalId, type }: SeriesIdLike): void;
    getVisiblePeerGroupIndex({ type, seriesGrouping, visible }: SeriesLike): SeriesGroupingResult;
}
export {};
