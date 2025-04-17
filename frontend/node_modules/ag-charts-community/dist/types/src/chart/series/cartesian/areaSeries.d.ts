import type { AgSeriesMarkerStyle } from 'ag-charts-types';
import type { ModuleContext } from '../../../module/moduleContext';
import { BBox } from '../../../scene/bbox';
import { Group } from '../../../scene/group';
import type { Node } from '../../../scene/node';
import type { Selection } from '../../../scene/selection';
import type { Path } from '../../../scene/shape/path';
import type { Text } from '../../../scene/shape/text';
import { ChartAxisDirection } from '../../chartAxisDirection';
import type { DataController } from '../../data/dataController';
import type { CategoryLegendDatum, ChartLegendType } from '../../legend/legendDatum';
import type { LegendSymbolOptions } from '../../legend/legendSymbol';
import type { Marker } from '../../marker/marker';
import { type TooltipContent } from '../../tooltip/tooltip';
import { type PickFocusInputs } from '../series';
import { AreaSeriesProperties } from './areaSeriesProperties';
import { type AreaSeriesNodeDataContext, type LabelSelectionDatum, type MarkerSelectionDatum } from './areaUtil';
import type { CartesianAnimationData } from './cartesianSeries';
import { CartesianSeries } from './cartesianSeries';
type AreaAnimationData = CartesianAnimationData<Group, MarkerSelectionDatum, LabelSelectionDatum, AreaSeriesNodeDataContext>;
export declare class AreaSeries extends CartesianSeries<Group, AreaSeriesProperties, MarkerSelectionDatum, LabelSelectionDatum, AreaSeriesNodeDataContext> {
    static readonly className = "AreaSeries";
    static readonly type: "area";
    properties: AreaSeriesProperties;
    connectsToYAxis: boolean;
    readonly backgroundGroup: Group<any>;
    get pickModeAxis(): "main";
    constructor(moduleCtx: ModuleContext);
    renderToOffscreenCanvas(): boolean;
    attachSeries(seriesContentNode: Node, seriesNode: Node, annotationNode: Node | undefined): void;
    detachSeries(seriesContentNode: Node | undefined, seriesNode: Node, annotationNode: Node | undefined): void;
    protected attachPaths([fill, stroke]: Path[]): void;
    protected detachPaths([fill, stroke]: Path[]): void;
    private isStacked;
    private _isStacked;
    setSeriesIndex(index: number): boolean;
    processData(dataController: DataController): Promise<void>;
    xCoordinateRange(xValue: any, pixelSize: number): [number, number];
    yCoordinateRange(yValues: any[], pixelSize: number): [number, number];
    getSeriesDomain(direction: ChartAxisDirection): any[];
    getSeriesRange(_direction: ChartAxisDirection, visibleRange: [any, any]): [number, number];
    getVisibleItems(xVisibleRange: [number, number], yVisibleRange: [number, number], minVisibleItems: number): number;
    createNodeData(): AreaSeriesNodeDataContext | undefined;
    protected isPathOrSelectionDirty(): boolean;
    protected updatePathNodes(opts: {
        paths: Path[];
        opacity: number;
        visible: boolean;
        animationEnabled: boolean;
    }): void;
    protected updatePaths(opts: {
        contextData: AreaSeriesNodeDataContext;
        paths: Path[];
    }): void;
    private updateAreaPaths;
    private updateFillPath;
    private updateStrokePath;
    protected updateMarkerSelection(opts: {
        nodeData: MarkerSelectionDatum[];
        markerSelection: Selection<Marker, MarkerSelectionDatum>;
    }): Selection<Marker, MarkerSelectionDatum>;
    protected updateMarkerNodes(opts: {
        markerSelection: Selection<Marker, MarkerSelectionDatum>;
        isHighlight: boolean;
    }): void;
    protected updateLabelSelection(opts: {
        labelData: LabelSelectionDatum[];
        labelSelection: Selection<Text, LabelSelectionDatum>;
    }): Selection<Text<any>, LabelSelectionDatum>;
    protected updateLabelNodes(opts: {
        labelSelection: Selection<Text, LabelSelectionDatum>;
    }): void;
    getTooltipContent(datumIndex: number): TooltipContent | undefined;
    legendItemSymbol(): LegendSymbolOptions;
    getLegendData(legendType: ChartLegendType): CategoryLegendDatum[];
    animateEmptyUpdateReady(animationData: AreaAnimationData): void;
    protected animateReadyResize(animationData: AreaAnimationData): void;
    animateWaitingUpdateReady(animationData: AreaAnimationData): void;
    protected isLabelEnabled(): boolean;
    protected nodeFactory(): Group<any>;
    getFormattedMarkerStyle(datum: MarkerSelectionDatum): AgSeriesMarkerStyle & {
        size: number;
    };
    protected computeFocusBounds(opts: PickFocusInputs): BBox | undefined;
}
export {};
