import type { NodeUpdateState } from '../../../motion/fromToMotion';
import { BBox } from '../../../scene/bbox';
import type { Node } from '../../../scene/node';
import type { Point, SizedPoint } from '../../../scene/point';
import type { Selection } from '../../../scene/selection';
import type { AnimationManager } from '../../interaction/animationManager';
import type { PickFocusInputs } from '../series';
import type { ISeries, NodeDataDependant, SeriesNodeDatum } from '../seriesTypes';
import type { CartesianSeriesNodeDatum } from './cartesianSeries';
type NodeWithOpacity = Node & {
    opacity: number;
};
export declare function markerFadeInAnimation<T>({ id }: {
    id: string;
}, animationManager: AnimationManager, status?: NodeUpdateState, ...markerSelections: Selection<NodeWithOpacity, T>[]): void;
export declare function markerScaleInAnimation<T>({ id }: {
    id: string;
}, animationManager: AnimationManager, ...markerSelections: Selection<Node, T>[]): void;
export declare function markerSwipeScaleInAnimation<T extends CartesianSeriesNodeDatum>({ id, nodeDataDependencies }: {
    id: string;
} & NodeDataDependant, animationManager: AnimationManager, ...markerSelections: Selection<Node, T>[]): void;
export declare function resetMarkerFn(_node: NodeWithOpacity & Node): {
    opacity: number;
    scalingX: number;
    scalingY: number;
};
export declare function resetMarkerPositionFn<T extends CartesianSeriesNodeDatum>(_node: Node, datum: T): {
    x: number;
    y: number;
    scalingCenterX: number;
    scalingCenterY: number;
};
interface MarkerNodeDatum extends SeriesNodeDatum<unknown> {
    readonly point: Point & SizedPoint;
}
interface MarkerSeries<TDatum extends MarkerNodeDatum> extends ISeries<number, TDatum, unknown, unknown> {
    getNodeData(): {
        [index: number]: TDatum | undefined;
    } | undefined;
    getFormattedMarkerStyle(datum: TDatum): {
        size: number;
    };
}
export declare function computeMarkerFocusBounds<TDatum extends MarkerNodeDatum>(series: MarkerSeries<TDatum>, { datumIndex }: PickFocusInputs): BBox | undefined;
export {};
