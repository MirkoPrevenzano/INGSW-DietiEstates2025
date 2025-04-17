import type { AgPatternName } from 'ag-charts-types';
import type { ExtendedPath2D } from '../extendedPath2D';
interface PatternFnParams {
    width: number;
    height: number;
    pixelRatio: number;
    strokeWidth: number;
    padding: number;
}
export declare const PATTERNS: Record<AgPatternName, (path: ExtendedPath2D, params: PatternFnParams) => void>;
export {};
