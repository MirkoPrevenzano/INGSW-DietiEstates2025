/**
 * Retrieves the global `document` object or a specific property of it.
 * @param propertyName - The name of the `document` property to retrieve (optional).
 * @returns The `document` object or the specified property.
 */
export declare function getDocument<E>(): Document & E;
export declare function getDocument<K extends keyof Document>(propertyName: K): Document[K];
/**
 * Retrieves the global `window` object or a specific property of it.
 * @param propertyName - The name of the `window` property to retrieve (optional).
 * @returns The `window` object or the specified property.
 */
export declare function getWindow<E>(): Window & E;
export declare function getWindow<K extends keyof Window>(propertyName: K): Window[K];
export declare function getWindow<R = unknown>(propertyName: string): R;
/**
 * Sets the global `document` object.
 * @param document - The `document` object to set.
 */
export declare function setDocument(document: Document): void;
/**
 * Sets the global `window` object.
 * @param window - The `window` object to set.
 */
export declare function setWindow(window: Window): void;
