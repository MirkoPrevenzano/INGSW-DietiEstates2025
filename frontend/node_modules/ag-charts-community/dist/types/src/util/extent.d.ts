export declare function extent(values: Array<unknown>): [number, number] | null;
export declare function normalisedExtentWithMetadata(d: number[], min?: number, max?: number): {
    extent: number[];
    clipped: boolean;
};
