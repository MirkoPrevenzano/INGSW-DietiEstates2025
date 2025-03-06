export declare const interpolate: unique symbol;
export declare const isInterpolating: (x: any) => x is Interpolating<any>;
export interface Interpolating<T = any> {
    equals(other: T): boolean;
    [interpolate]: (other: T, d: number) => T;
}
