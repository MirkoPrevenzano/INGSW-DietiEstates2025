import type { ExtendedPath2D } from '../../../scene/extendedPath2D';
import { type Span, SpanJoin } from './lineInterpolation';
export declare function plotSpan(path: ExtendedPath2D, span: Span, moveTo: SpanJoin, reversed: boolean): void;
export declare function interpolatedSpanRange(a: Span, b: Span, ratio: number): {
    x: number;
    y: number;
}[];
export declare function plotInterpolatedSpans(path: ExtendedPath2D, a: Span, b: Span, ratio: number, moveTo: SpanJoin, reversed: boolean): void;
