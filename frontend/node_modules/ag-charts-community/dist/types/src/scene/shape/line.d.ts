import type { DistantObject } from '../../util/nearest';
import { BBox } from '../bbox';
import type { NodeOptions, RenderContext } from '../node';
import { Shape } from './shape';
export declare class Line extends Shape implements DistantObject {
    static readonly className = "Line";
    protected static defaultStyles: {
        fill: undefined;
        strokeWidth: number;
        stroke?: import("./shape").ShapeColor | undefined;
        lineDash?: number[] | undefined;
        lineDashOffset: number;
        lineCap?: import("./shape").ShapeLineCap | undefined;
        lineJoin?: import("./shape").ShapeLineJoin | undefined;
        opacity: number;
        fillShadow?: import("../dropShadow").DropShadow | undefined;
    };
    constructor(opts?: NodeOptions);
    x1: number;
    y1: number;
    x2: number;
    y2: number;
    set x(value: number);
    set y(value: number);
    get midPoint(): {
        x: number;
        y: number;
    };
    protected computeBBox(): BBox;
    isPointInPath(x: number, y: number): boolean;
    distanceSquared(px: number, py: number): number;
    render(renderCtx: RenderContext): void;
    toSVG(): {
        elements: SVGElement[];
        defs?: SVGElement[];
    } | undefined;
}
