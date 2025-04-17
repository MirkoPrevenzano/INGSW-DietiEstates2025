import type { BBox } from '../bbox';
import { type ColorSpace, Gradient, type GradientParams } from './gradient';
import type { GradientColorStop } from './stops';
export declare class RadialGradient extends Gradient {
    constructor(colorSpace: ColorSpace, stops: GradientColorStop[], bbox?: BBox);
    protected createCanvasGradient(ctx: CanvasRenderingContext2D, bbox: BBox, params?: GradientParams): CanvasGradient | undefined;
    createSvgGradient(bbox: BBox): SVGRadialGradientElement;
}
