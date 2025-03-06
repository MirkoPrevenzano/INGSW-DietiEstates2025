import type { RenderContext } from './node';
import { Node } from './node';
export declare class Image<D = any> extends Node<D> {
    private readonly sourceImage?;
    constructor(sourceImage?: HTMLImageElement | ImageBitmap | undefined);
    x: number;
    y: number;
    width: number;
    height: number;
    opacity: number;
    render(renderCtx: RenderContext): void;
}
