export declare const IGNORED_PROP: unique symbol;
export declare function pickProps<T>(opts: Partial<T>, values: {
    [K in keyof Required<T>]: (T[K] extends Required<T[K]> ? T[K] : T[K] | undefined) | typeof IGNORED_PROP;
}): any;
