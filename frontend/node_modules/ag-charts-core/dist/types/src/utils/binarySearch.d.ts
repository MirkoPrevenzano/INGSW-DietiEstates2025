/**
 * Finds the maximum value that matches a condition within a specified range.
 *
 * @template T
 * @param {number} min - The minimum number in the range.
 * @param {number} max - The maximum number in the range.
 * @param {function(number): boolean} iteratee - A function that takes a number and returns a value of type T or undefined.
 * @returns {number | undefined} - The maximum value that matches the condition, or undefined if no match is found.
 */
export declare function findMaxIndex(min: number, max: number, iteratee: (value: number) => boolean): number | undefined;
/**
 * Finds the minimum value that matches a condition within a specified range.
 *
 * @template T
 * @param {number} min - The minimum number in the range.
 * @param {number} max - The maximum number in the range.
 * @param {function(number): boolean} iteratee - A function that takes a number and returns a value of type T or undefined.
 * @returns {number | undefined} - The minimum value that matches the condition, or undefined if no match is found.
 */
export declare function findMinIndex(min: number, max: number, iteratee: (value: number) => boolean): number | undefined;
/**
 * Finds the maximum value that matches a condition within a specified range.
 *
 * @template T
 * @param {number} min - The minimum number in the range.
 * @param {number} max - The maximum number in the range.
 * @param {function(number): (T | undefined)} iteratee - A function that takes a number and returns a value of type T or undefined.
 * @returns {T | undefined} - The maximum value that matches the condition, or undefined if no match is found.
 */
export declare function findMaxValue<T>(min: number, max: number, iteratee: (value: number) => T | undefined): T | undefined;
/**
 * Finds the minimum value that matches a condition within a specified range.
 *
 * @template T
 * @param {number} min - The minimum number in the range.
 * @param {number} max - The maximum number in the range.
 * @param {function(number): (T | undefined)} iteratee - A function that takes a number and returns a value of type T or undefined.
 * @returns {T | undefined} - The minimum value that matches the condition, or undefined if no match is found.
 */
export declare function findMinValue<T>(min: number, max: number, iteratee: (value: number) => T | undefined): T | undefined;
