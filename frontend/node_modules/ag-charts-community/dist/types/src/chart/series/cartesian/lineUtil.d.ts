import { type FromToFns, type NodeUpdateState } from '../../../motion/fromToMotion';
import type { Point } from '../../../scene/point';
import type { Path } from '../../../scene/shape/path';
import type { ProcessedOutputDiff } from '../../data/dataModel';
import type { ErrorBoundSeriesNodeDatum } from '../seriesTypes';
import type { CartesianSeriesNodeDataContext, CartesianSeriesNodeDatum } from './cartesianSeries';
import type { InterpolationProperties } from './interpolationProperties';
import { type Span } from './lineInterpolation';
import { type SpanInterpolation } from './lineInterpolationUtil';
export type LinePathSpan = {
    span: Span;
    xValue0: any;
    yValue0: any;
    xValue1: any;
    yValue1: any;
};
export type LineStrokePathDatum = {
    readonly spans: LinePathSpan[];
    readonly itemId: string;
};
export interface SpanAnimation {
    added: SpanInterpolation[];
    moved: SpanInterpolation[];
    removed: SpanInterpolation[];
}
export type LineSpanPointDatum = {
    point: Point;
    xDatum: any;
    yDatum: any;
};
export interface LineNodeDatum extends CartesianSeriesNodeDatum, ErrorBoundSeriesNodeDatum {
    readonly xValue: NonNullable<CartesianSeriesNodeDatum['xValue']>;
    readonly yValue: NonNullable<CartesianSeriesNodeDatum['yValue']>;
    readonly point: NonNullable<CartesianSeriesNodeDatum['point']>;
    readonly labelText?: string;
    readonly selected: boolean | undefined;
}
export interface LineSeriesNodeDataContext extends CartesianSeriesNodeDataContext<LineNodeDatum> {
    strokeData?: LineStrokePathDatum;
    crossFiltering: boolean;
}
export declare function interpolatePoints(points: LineSpanPointDatum[], interpolation: InterpolationProperties): LinePathSpan[];
export declare function plotLinePathStroke({ path }: Path, spans: LinePathSpan[]): void;
export declare function plotInterpolatedLinePathStroke(ratio: number, path: Path, spans: SpanInterpolation[]): void;
export declare function prepareLinePathStrokeAnimationFns(status: NodeUpdateState, spans: SpanAnimation, visibleToggleMode: 'fade' | 'none'): {
    status: NodeUpdateState;
    path: {
        addPhaseFn: (ratio: number, path: Path) => void;
        updatePhaseFn: (ratio: number, path: Path) => void;
        removePhaseFn: (ratio: number, path: Path) => void;
    };
    pathProperties: FromToFns<Path<any>, any, unknown>;
};
export declare function prepareLinePathPropertyAnimation(status: NodeUpdateState, visibleToggleMode: 'fade' | 'none'): FromToFns<Path, any, unknown>;
export declare function prepareLinePathAnimation(newData: LineSeriesNodeDataContext, oldData: LineSeriesNodeDataContext, diff: ProcessedOutputDiff | undefined): {
    status: NodeUpdateState;
    stroke: {
        status: NodeUpdateState;
        path: {
            addPhaseFn: (ratio: number, path: Path<any>) => void;
            updatePhaseFn: (ratio: number, path: Path<any>) => void;
            removePhaseFn: (ratio: number, path: Path<any>) => void;
        };
        pathProperties: FromToFns<Path<any>, any, unknown>;
    };
    hasMotion: boolean;
} | undefined;
