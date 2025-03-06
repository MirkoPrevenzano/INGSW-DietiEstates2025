import type { Point } from '../point';
export declare function evaluateBezier(p0: number, p1: number, p2: number, p3: number, t: number): number;
export declare function solveBezier(p0: number, p1: number, p2: number, p3: number, value: number): number;
export declare function splitBezier(p0x: number, p0y: number, p1x: number, p1y: number, p2x: number, p2y: number, p3x: number, p3y: number, t: number): [[Point, Point, Point, Point], [Point, Point, Point, Point]];
export declare function calculateDerivativeExtrema(p0: number, p1: number, p2: number, p3: number): number[];
export declare function calculateDerivativeExtremaXY(sx: number, sy: number, cp1x: number, cp1y: number, cp2x: number, cp2y: number, x: number, y: number): number[];
