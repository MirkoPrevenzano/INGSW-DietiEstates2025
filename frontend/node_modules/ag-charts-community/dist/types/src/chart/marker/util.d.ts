import type { AgMarkerShape, AgMarkerShapeFn } from 'ag-charts-types';
type MarkerSupportedShapes = Exclude<AgMarkerShape, AgMarkerShapeFn>;
export declare function isSupportedMarkerShape(shape: unknown): shape is MarkerSupportedShapes;
export {};
