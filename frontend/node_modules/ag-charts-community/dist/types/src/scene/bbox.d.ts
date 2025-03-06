import type { BBoxContainsTester } from '../util/bboxinterface';
import { BBoxValues } from '../util/bboxinterface';
import { type Interpolating, interpolate } from '../util/interpolating';
import type { DistantObject, NearestResult } from '../util/nearest';
type Padding = {
    top: number;
    left: number;
    right: number;
    bottom: number;
};
type ShrinkOrGrowPosition = 'top' | 'left' | 'bottom' | 'right' | 'vertical' | 'horizontal';
export declare class BBox implements BBoxValues, BBoxContainsTester, DistantObject, Interpolating<BBox> {
    x: number;
    y: number;
    width: number;
    height: number;
    static readonly zero: BBox;
    static readonly NaN: BBox;
    static fromDOMRect({ x, y, width, height }: DOMRect): BBox;
    static merge(boxes: Iterable<BBoxValues>): BBox;
    static nearestBox(x: number, y: number, boxes: BBox[]): NearestResult<BBox>;
    constructor(x: number, y: number, width: number, height: number);
    toDOMRect(): DOMRect;
    clone(): BBox;
    equals(other: BBox): boolean;
    containsPoint(x: number, y: number): boolean;
    intersection(other: BBox): BBox | undefined;
    collidesBBox(other: BBox): boolean;
    computeCenter(): {
        x: number;
        y: number;
    };
    isFinite(): boolean;
    distanceSquared(x: number, y: number): number;
    shrink(amounts: Partial<Padding>): this;
    shrink(amount: number, position?: ShrinkOrGrowPosition): this;
    grow(amounts: Partial<Padding>): this;
    grow(amount: number, position?: ShrinkOrGrowPosition): this;
    private applyMargin;
    translate(x: number, y: number): this;
    [interpolate](other: BBox, d: number): BBox;
}
export {};
