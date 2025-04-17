import type { ModuleContext } from '../../../module/moduleContext';
import { ColorScale } from '../../../scale/colorScale';
import { BBox } from '../../../scene/bbox';
import type { Group } from '../../../scene/group';
import type { Node } from '../../../scene/node';
import type { Point } from '../../../scene/point';
import type { Selection } from '../../../scene/selection';
import type { Path } from '../../../scene/shape/path';
import { StateMachine } from '../../../util/stateMachine';
import type { ChartAnimationPhase } from '../../chartAnimationPhase';
import type { ChartAxisDirection } from '../../chartAxisDirection';
import type { HighlightNodeDatum } from '../../interaction/highlightManager';
import type { ChartLegendType, GradientLegendDatum } from '../../legend/legendDatum';
import { type PickFocusInputs, type PickFocusOutputs, Series } from '../series';
import type { ISeries, SeriesNodeDatum } from '../seriesTypes';
import type { HierarchySeriesProperties } from './hierarchySeriesProperties';
type HierarchyAnimationState = 'empty' | 'ready' | 'waiting' | 'clearing';
type HierarchyAnimationEvent<TNode extends Node, TDatum> = {
    update: HierarchyAnimationData<TNode, TDatum>;
    updateData: undefined;
    highlight: Selection<TNode, TDatum>;
    resize: HierarchyAnimationData<TNode, TDatum>;
    clear: HierarchyAnimationData<TNode, TDatum>;
    reset: undefined;
    skip: undefined;
};
export interface HierarchyNodeDatum extends SeriesNodeDatum<number[]> {
}
export interface HierarchyAnimationData<_TNode extends Node, _TNodeClass> {
}
export declare class HierarchyNode<This extends HierarchyNode<This, TDatum> = any, TDatum = Record<string, any>> implements HierarchyNodeDatum, Pick<HighlightNodeDatum, 'colorValue'> {
    readonly series: ISeries<any, any, any>;
    readonly datumIndex: number[];
    readonly datum: TDatum | undefined;
    readonly sizeValue: number;
    readonly colorValue: number | undefined;
    readonly sumSize: number;
    readonly depth: number | undefined;
    readonly parent: This | undefined;
    readonly children: This[];
    private static readonly Walk;
    readonly midPoint: Point;
    constructor(series: ISeries<any, any, any>, datumIndex: number[], datum: TDatum | undefined, sizeValue: number, colorValue: number | undefined, sumSize: number, depth: number | undefined, parent: This | undefined, children: This[]);
    get hasChildren(): boolean;
    walk(callback: (node: This) => void, order?: number): void;
    [Symbol.iterator](): Iterator<This>;
}
export declare abstract class HierarchySeries<TNode extends Node = Group, TProps extends HierarchySeriesProperties<any> = HierarchySeriesProperties<any>, TNodeClass extends HierarchyNode = HierarchyNode> extends Series<number[], TNodeClass, TProps> {
    protected abstract NodeClass: new (...params: ConstructorParameters<typeof HierarchyNode<any, any>>) => TNodeClass;
    rootNode: TNodeClass | undefined;
    colorDomain: number[];
    maxDepth: number;
    protected colorScale: ColorScale;
    protected animationState: StateMachine<HierarchyAnimationState, HierarchyAnimationEvent<TNode, TNodeClass>>;
    constructor(moduleCtx: ModuleContext);
    resetAnimation(phase: ChartAnimationPhase): void;
    processData(): void;
    protected abstract updateSelections(): void;
    protected abstract updateNodes(): void;
    update({ seriesRect }: {
        seriesRect?: BBox;
    }): void;
    protected resetAllAnimation(_data: HierarchyAnimationData<TNode, TNodeClass>): void;
    protected animateEmptyUpdateReady(data: HierarchyAnimationData<TNode, TNodeClass>): void;
    protected animateWaitingUpdateReady(data: HierarchyAnimationData<TNode, TNodeClass>): void;
    protected animateReadyHighlight(_data: Selection<TNode, TNodeClass>): void;
    protected animateReadyResize(data: HierarchyAnimationData<TNode, TNodeClass>): void;
    protected animateClearingUpdateEmpty(data: HierarchyAnimationData<TNode, TNodeClass>): void;
    protected getAnimationData(): HierarchyAnimationData<TNode, TNodeClass>;
    protected isProcessedDataAnimatable(): boolean;
    protected checkProcessedDataAnimatable(): void;
    getSeriesDomain(): number[];
    getSeriesRange(_direction: ChartAxisDirection, _visibleRange: [any, any]): [number, number];
    getLegendData(legendType: ChartLegendType): GradientLegendDatum[];
    protected getDatumIdFromData(node: TNodeClass): string;
    protected getDatumId(node: TNodeClass): string;
    private removeMeIndexPathForIndex;
    private removeMeIndexForIndexPath;
    protected abstract datumSelection: Selection<any, TNodeClass>;
    protected abstract computeFocusBounds(node: TNode): BBox | Path | undefined;
    pickFocus(opts: PickFocusInputs): PickFocusOutputs | undefined;
    getDatumAriaText(datum: SeriesNodeDatum<number>, description: string): string | undefined;
    getCategoryValue(_datumIndex: number[]): void;
    datumIndexForCategoryValue(_categoryValue: any): number[] | undefined;
}
export {};
