import type { Point } from '../../../scene/point';
export type LinearSpan = {
    type: 'linear';
    moveTo: boolean;
    x0: number;
    y0: number;
    x1: number;
    y1: number;
};
export type CubicSpan = {
    type: 'cubic';
    moveTo: boolean;
    cp0x: number;
    cp0y: number;
    cp1x: number;
    cp1y: number;
    cp2x: number;
    cp2y: number;
    cp3x: number;
    cp3y: number;
};
export type StepSpan = {
    type: 'step';
    moveTo: boolean;
    x0: number;
    y0: number;
    x1: number;
    y1: number;
    stepX: number;
};
export type Span = LinearSpan | CubicSpan | StepSpan;
export declare function spanRange(span: Span): [Point, Point];
export declare function collapseSpanToPoint(span: Span, point: Point): Span;
export declare function rescaleSpan(span: Span, nextStart: Point, nextEnd: Point): Span;
export declare function clipSpanX(span: Span, x0: number, x1: number): Span;
export declare enum SpanJoin {
    MoveTo = 0,
    LineTo = 1
}
export declare function linearPoints(points: Iterable<Point>): Span[];
declare const lineSteps: {
    start: number;
    middle: number;
    end: number;
};
export declare function stepPoints(points: Iterable<Point>, position: number | keyof typeof lineSteps): Span[];
export declare function smoothPoints(iPoints: Iterable<Point>, tension: number): Span[];
export {};
