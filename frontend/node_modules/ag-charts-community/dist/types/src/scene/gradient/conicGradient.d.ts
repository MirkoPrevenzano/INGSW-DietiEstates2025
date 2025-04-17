import type { BBox } from '../bbox';
import { type ColorSpace, Gradient, type GradientParams } from './gradient';
import type { GradientColorStop } from './stops';
export declare class ConicGradient extends Gradient {
    angle: number;
    constructor(colorSpace: ColorSpace, stops: GradientColorStop[], angle?: number, bbox?: BBox);
    protected createCanvasGradient(ctx: CanvasRenderingContext2D, bbox: BBox, params?: GradientParams): CanvasGradient | undefined;
    createSvgGradient(_bbox: BBox): SVGLinearGradientElement;
}
