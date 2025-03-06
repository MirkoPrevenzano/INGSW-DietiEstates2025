import type { AgMarkerShape, AgMarkerShapeFn, AgMarkerShapeFnParams } from 'ag-charts-types';
export type MarkerPathMove = {
    x: number;
    y: number;
    t?: 'move';
};
export declare function drawMarkerUnitPolygon(params: AgMarkerShapeFnParams, moves: Array<readonly [number, number]>): void;
export declare const MARKER_SHAPES: Record<Exclude<AgMarkerShape, AgMarkerShapeFn>, AgMarkerShapeFn>;
