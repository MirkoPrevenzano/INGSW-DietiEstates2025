/**
 * Joins an array of strings or objects into a formatted string, adding a custom conjunction before the last item.
 * Useful for creating human-readable lists from arrays with an option to limit the number of items shown.
 * @param values An array of strings or objects to join. Objects are converted to strings using the `format` function.
 * @param conjunction The word to use before the last item in the list, defaulting to 'and'.
 * @param format A function that formats each item in the array. By default, items are converted to strings using `String`.
 * @param maxItems The maximum number of items to show before truncating, defaults to showing all items.
 * @returns A string that is the result of joining the formatted values with commas, truncating if necessary, and appending the specified conjunction before the last item.
 */
export declare function joinFormatted(values: string[], conjunction?: string, format?: (value: any) => string, maxItems?: number): string;
/**
 * Converts a value to a string with an optional maximum length.
 * Provides specific string representations for `undefined`, `NaN`, `Infinity`, and `-Infinity`.
 * If the stringified value exceeds the specified maximum length, it truncates the string and appends an indication of the truncated length.
 * @param value The value to be stringified. Can be of any type.
 * @param maxLength The maximum length of the resulting string. Defaults to Infinity.
 * @returns A string representation of the value, potentially truncated if it exceeds the maximum length.
 */
export declare function stringifyValue(value: unknown, maxLength?: number): string;
/**
 * Efficiently counts the number of lines in a string.
 * Processes the string in a single pass, counting newline (`\n`) characters
 * by their ASCII value (10). Optimized for performance in scenarios with
 * large datasets or tight loops.
 * @param {string} text The input string.
 * @returns {number} The number of lines, with at least one line for non-empty strings.
 */
export declare function countLines(text: string): number;
/**
 * Computes the Levenshtein distance between two strings.
 * This function uses a space-optimized dynamic programming approach.
 *
 * @param {string} a - The first string.
 * @param {string} b - The second string.
 * @returns {number} - The Levenshtein distance between the two strings.
 */
export declare function levenshteinDistance(a: string, b: string): number;
