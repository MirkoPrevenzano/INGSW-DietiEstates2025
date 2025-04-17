import type { AgGradientColorMode, AgGradientColorStop } from 'ag-charts-types';
import { BaseProperties } from '../../util/properties';
export interface GradientColorStop {
    stop: number;
    color: string;
}
export declare class StopProperties extends BaseProperties implements AgGradientColorStop {
    stop?: number;
    color?: string;
}
export declare function getColorStops(baseFills: Array<AgGradientColorStop | string>, defaultColorStops: string[], domain: number[], fillMode?: AgGradientColorMode): GradientColorStop[];
