import type { BBox } from '../bbox';
import type { GradientColorStop } from './stops';
export type ColorSpace = 'rgb' | 'oklch';
export interface GradientParams {
    centerX?: number;
    centerY?: number;
    innerRadius?: number;
    outerRadius?: number;
}
export declare abstract class Gradient {
    colorSpace: ColorSpace;
    stops: GradientColorStop[];
    private readonly bbox?;
    constructor(colorSpace: ColorSpace, stops?: GradientColorStop[], bbox?: BBox | undefined);
    protected abstract createCanvasGradient(ctx: CanvasRenderingContext2D, bbox: BBox, params?: GradientParams): CanvasGradient | undefined;
    private _cache;
    createGradient(ctx: CanvasRenderingContext2D, shapeBbox: BBox, params?: GradientParams): CanvasGradient | string | undefined;
    protected abstract createSvgGradient(bbox: BBox): SVGElement;
    toSvg(shapeBbox: BBox): SVGElement;
}
