import type { CartesianSeriesNodeDataContext } from './cartesianSeries';
import { type Span } from './lineInterpolation';
import type { Scaling } from './scaling';
export interface SpanDatum {
    span: Span;
    xValue0: any;
    yValue0: any;
    xValue1: any;
    yValue1: any;
}
export interface SpanContext {
    scales: CartesianSeriesNodeDataContext['scales'];
    data: SpanDatum[];
    zeroData?: SpanDatum[];
}
export interface SpanInterpolation {
    from: Span;
    to: Span;
}
export interface SpanInterpolationResult {
    removed: SpanInterpolation[];
    moved: SpanInterpolation[];
    added: SpanInterpolation[];
}
export declare enum CollapseMode {
    Zero = 0,
    Split = 1
}
export declare function scale(val: number | string | Date, scaling?: Scaling): number;
export declare function pairUpSpans(newData: SpanContext, oldData: SpanContext, collapseMode: CollapseMode): SpanInterpolationResult | undefined;
