import type { InternalAgColorType } from 'ag-charts-core';
import type { NodeUpdateState } from '../../../motion/fromToMotion';
import type { Point, SizedPoint } from '../../../scene/point';
import type { Path } from '../../../scene/shape/path';
import type { SeriesNodeDatum } from '../seriesTypes';
import type { CartesianSeriesNodeDataContext, CartesianSeriesNodeDatum } from './cartesianSeries';
import { type SpanInterpolation } from './lineInterpolationUtil';
import { type LinePathSpan, type SpanAnimation } from './lineUtil';
export type AreaFillPathDatum = {
    readonly spans: LinePathSpan[];
    readonly phantomSpans: LinePathSpan[];
    readonly itemId: string;
};
export type AreaStrokePathDatum = {
    readonly spans: LinePathSpan[];
    readonly itemId: string;
};
export interface MarkerSelectionDatum extends CartesianSeriesNodeDatum {
    readonly xValue: NonNullable<CartesianSeriesNodeDatum['xValue']>;
    readonly yValue: NonNullable<CartesianSeriesNodeDatum['yValue']>;
    readonly point: Readonly<SizedPoint>;
    readonly yKey: string;
    readonly fill?: InternalAgColorType;
    readonly stroke?: string;
    readonly strokeWidth: number;
    readonly cumulativeValue: number;
    readonly selected: boolean | undefined;
}
export interface LabelSelectionDatum extends Readonly<Point>, SeriesNodeDatum<number> {
    readonly itemId: any;
    readonly labelText: string;
}
export interface AreaSeriesNodeDataContext extends CartesianSeriesNodeDataContext<MarkerSelectionDatum, LabelSelectionDatum> {
    fillData: AreaFillPathDatum;
    strokeData: AreaStrokePathDatum;
    stackVisible: boolean;
    crossFiltering: boolean;
}
export declare function plotAreaPathFill({ path }: Path, { spans, phantomSpans }: AreaFillPathDatum): void;
export declare function plotInterpolatedAreaSeriesFillSpans(ratio: number, { path }: Path, spans: SpanInterpolation[], fillPhantomSpans: SpanInterpolation[]): void;
export declare function prepareAreaFillAnimationFns(status: NodeUpdateState, spans: SpanAnimation, fillPhantomSpans: SpanAnimation, visibleToggleMode: 'fade' | 'none'): {
    status: NodeUpdateState;
    path: {
        addPhaseFn: (ratio: number, path: Path) => void;
        updatePhaseFn: (ratio: number, path: Path) => void;
        removePhaseFn: (ratio: number, path: Path) => void;
    };
    pathProperties: import("../../../motion/fromToMotion").FromToFns<Path<any>, any, unknown>;
};
export declare function prepareAreaPathAnimation(newData: AreaSeriesNodeDataContext, oldData: AreaSeriesNodeDataContext): {
    status: NodeUpdateState;
    fill: {
        status: NodeUpdateState;
        path: {
            addPhaseFn: (ratio: number, path: Path<any>) => void;
            updatePhaseFn: (ratio: number, path: Path<any>) => void;
            removePhaseFn: (ratio: number, path: Path<any>) => void;
        };
        pathProperties: import("../../../motion/fromToMotion").FromToFns<Path<any>, any, unknown>;
    };
    stroke: {
        status: NodeUpdateState;
        path: {
            addPhaseFn: (ratio: number, path: Path<any>) => void;
            updatePhaseFn: (ratio: number, path: Path<any>) => void;
            removePhaseFn: (ratio: number, path: Path<any>) => void;
        };
        pathProperties: import("../../../motion/fromToMotion").FromToFns<Path<any>, any, unknown>;
    };
} | undefined;
