export declare const Vec2: {
    add: typeof add;
    angle: typeof angle;
    apply: typeof apply;
    equal: typeof equal;
    distance: typeof distance;
    distanceSquared: typeof distanceSquared;
    from: typeof from;
    gradient: typeof gradient;
    intercept: typeof intercept;
    intersectAtX: typeof intersectAtX;
    intersectAtY: typeof intersectAtY;
    length: typeof length;
    lengthSquared: typeof lengthSquared;
    multiply: typeof multiply;
    normalized: typeof normalized;
    origin: typeof origin;
    required: typeof required;
    rotate: typeof rotate;
    round: typeof round;
    sub: typeof sub;
};
export interface Vec2 {
    x: number;
    y: number;
}
interface Vec4 {
    x1: number;
    y1: number;
    x2: number;
    y2: number;
}
/**
 * Add the components of the vectors `a` and `b`.
 */
declare function add(a: Vec2, b: Vec2): Vec2;
declare function add(a: Vec2, b: number): Vec2;
/**
 * Subtract the components of `b` from `a`.
 */
declare function sub(a: Vec2, b: Vec2): Vec2;
declare function sub(a: Vec2, b: number): Vec2;
/**
 * Multiply the components of `a` and `b`.
 */
declare function multiply(a: Vec2, b: Vec2): Vec2;
declare function multiply(a: Vec2, b: number): Vec2;
/**
 * Get the length of a vector.
 */
declare function length(a: Vec2): number;
/**
 * Get the squared length of a vector. This method is faster than `length(a)` and is useful when making comparisons
 * where the precise length does not matter.
 */
declare function lengthSquared(a: Vec2): number;
/**
 * Get the distance between two vectors.
 */
declare function distance(a: Vec2, b: Vec2): number;
/**
 * Get the squared distance between two vectors. This method is faster than `distance(a, b)` and is useful when making
 * comparisons where the precise distance does not matter.
 */
declare function distanceSquared(a: Vec2, b: Vec2): number;
/**
 * Normalize a vector so that each component is a value between 0 and 1 and the length of the vector is always 1.
 */
declare function normalized(a: Vec2): Vec2;
/**
 * Find the angle between two vectors.
 */
declare function angle(a: Vec2, b?: Vec2): number;
/**
 * Rotate vector `a` by the angle `theta` around the origin `b`.
 * This rotation is not cumulative, i.e. `rotate(rotate(a, Math.PI), Math.PI) !== a`.
 */
declare function rotate(a: Vec2, theta: number, b?: Vec2): Vec2;
/**
 * Get the gradient of the line that intersects two points.
 * Optionally reflect the line about the y-axis when the coordinate system has y = 0 at the top.
 */
declare function gradient(a: Vec2, b: Vec2, reflection?: number): number;
/**
 * Get the y-intercept of a line through a point with a gradient where `c = y - mx`.
 * Optionally reflect the line about the y-axis when the coordinate system has y = 0 at the top.
 */
declare function intercept(a: Vec2, gradient: number, reflection?: number): number;
/**
 * Get the point where a line intersects a horizontal line at the given y value.
 * Optionally reflect the line about the y-axis when the coordinate system has y = 0 at the top.
 */
declare function intersectAtY(gradient: number, coefficient: number, y?: number, reflection?: number): Vec2;
/**
 * Get the point where a line intersects a vertical line at the given x value.
 * Optionally reflect the line about the y-axis when the coordinate system has y = 0 at the top.
 */
declare function intersectAtX(gradient: number, coefficient: number, x?: number, reflection?: number): Vec2;
/**
 * Round each component of the vector.
 */
declare function round(a: Vec2, decimals?: number): Vec2;
/**
 * Check if the components of `a` and `b` are equal.
 */
declare function equal(a: Vec2, b: Vec2): boolean;
/**
 * Create a vector from an `x` and `y`.
 */
declare function from(x: number, y: number): Vec2;
/**
 * Create a vector from a widget event.
 */
declare function from(event: {
    currentX: number;
    currentY: number;
}): Vec2;
/**
 * Create a vector from a html element's `offsetWidth` and `offsetHeight`.
 */
declare function from(element: {
    offsetWidth: number;
    offsetHeight: number;
}): Vec2;
/**
 * Create a pair of vectors of the top left and bottom right of a bounding box.
 */
declare function from(bbox: {
    x: number;
    y: number;
    width: number;
    height: number;
}): [Vec2, Vec2];
/**
 * Create a pair of vectors from a line or box containing a pair of coordinates.
 */
declare function from(vec4: Vec4): [Vec2, Vec2];
/**
 * Apply the components of `b` to `a` and return `a`.
 */
declare function apply(a: Partial<Vec2>, b: Vec2): Vec2;
/**
 * Create a vector, defaulting the components to `0` if nullish.
 */
declare function required(a?: Partial<Vec2>): Vec2;
/**
 * Create a vector at the origin point (0,0).
 */
declare function origin(): Vec2;
export {};
