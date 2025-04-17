export declare function clamp(min: number, value: number, max: number): number;
export declare function inRange(value: number, range: [number, number], epsilon?: number): boolean;
export declare function isNumberEqual(a: number, b: number, epsilon?: number): boolean;
export declare function isNegative(value: number): boolean;
export declare function isInteger(value: number): boolean;
export declare function roundTo(value: number, decimals?: number): number;
/**
 * Returns the mathematically correct n modulus of m. For context, the JS % operator is remainder
 * NOT modulus, which is why this is needed.
 */
export declare function modulus(n: number, m: number): number;
export declare function countFractionDigits(value: number): number;
