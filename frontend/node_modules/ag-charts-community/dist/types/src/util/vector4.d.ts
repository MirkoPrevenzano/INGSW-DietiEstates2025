export declare const Vec4: {
    bottomCenter: typeof bottomCenter;
    center: typeof center;
    clone: typeof clone;
    collides: typeof collides;
    end: typeof end;
    from: typeof from;
    height: typeof height;
    round: typeof round;
    start: typeof start;
    topCenter: typeof topCenter;
    origin: typeof origin;
    width: typeof width;
};
/**
 * An interface for a vector with 4 components, considered to be a pair of vector2 coordinates. This can represent a
 * line with a start and end point, or two opposite corners of a box. There is no inherent ordering of the values
 * for each component, though positional functions such as `topCenter` will consider the min or max of each pair
 * of `x` and `y` components.
 */
export interface Vec4 {
    x1: number;
    y1: number;
    x2: number;
    y2: number;
}
interface Vec2 {
    x: number;
    y: number;
}
/**
 * Get the vector2 at the start of a vector4.
 */
declare function start(a: Vec4): Vec2;
/**
 * Get the vector2 at the end of a vector4.
 */
declare function end(a: Vec4): Vec2;
/**
 * Get the vector2 at top center of a vector4.
 */
declare function topCenter(a: Vec4): Vec2;
/**
 * Get the vector2 at center of a vector4.
 */
declare function center(a: Vec4): Vec2;
/**
 * Get the vector2 at bottom center of a vector4.
 */
declare function bottomCenter(a: Vec4): Vec2;
/**
 * Get the absolute width of a vector4.
 */
declare function width(a: Vec4): number;
/**
 * Get the absolute height of a vector4.
 */
declare function height(a: Vec4): number;
/**
 * Round each component of the vector4 to the nearest integer.
 */
declare function round(a: Vec4): Vec4;
/**
 * Clone a vector4.
 */
declare function clone(a: Vec4): Vec4;
declare function collides(a: Vec4, b: Vec4): boolean;
/**
 * Create a vector4 from an `x1`, `y1`, `x2` and `y2`.
 */
declare function from(x1: number, y1: number, x2: number, y2: number): Vec4;
/**
 * Create a vector4 from a bounding box.
 */
declare function from(bbox: {
    x: number;
    y: number;
    width: number;
    height: number;
}): Vec4;
/**
 * Create a vector4 at the origin point (0,0).
 */
declare function origin(): Vec4;
export {};
