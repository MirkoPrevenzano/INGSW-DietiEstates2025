import type { BBox } from '../bbox';
import { type ColorSpace, Gradient } from './gradient';
import type { GradientColorStop } from './stops';
export declare class LinearGradient extends Gradient {
    angle: number;
    constructor(colorSpace: ColorSpace, stops: GradientColorStop[], angle?: number, bbox?: BBox);
    private getGradientPoints;
    protected createCanvasGradient(ctx: CanvasRenderingContext2D, bbox: BBox): CanvasGradient | undefined;
    createSvgGradient(bbox: BBox): SVGLinearGradientElement;
}
