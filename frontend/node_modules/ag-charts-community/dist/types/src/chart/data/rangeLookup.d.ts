export declare class RangeLookup {
    private readonly maxLevelSize;
    private readonly buffer;
    constructor(allValues: number[][]);
    private computeRangeInto;
    rangeBetween(start: number, end: number): [number, number];
    get range(): [number, number];
}
