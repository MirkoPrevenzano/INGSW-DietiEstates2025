/**
 * Compares two arrays and returns the differences between them, identifying added and removed elements.
 *
 * @template T - The type of elements in the arrays.
 * @param {T[]} previous - The original array before changes.
 * @param {T[]} current - The modified array after changes.
 * @returns {{ changed: boolean, added: Set<T>, removed: Set<T> }}
 * - An object containing:
 *   - `changed`: A boolean indicating if there are any changes.
 *   - `added`: A set of elements that were added to the current array.
 *   - `removed`: A set of elements that were removed from the previous array.
 */
export declare function diffArrays<T>(previous: T[], current: T[]): {
    changed: boolean;
    added: Set<T>;
    removed: Set<T>;
};
