import type { BBoxValues } from '../../util/bboxinterface';
import type { ErrorBoundSeriesNodeDatum, ISeries, SeriesNodeDatum } from './seriesTypes';
export declare function datumStylerProperties<TDatum extends {
    xValue: any;
    yValue: any;
}>(datum: TDatum, xKey: string, yKey: string, xDomain: any[], yDomain: any[]): {
    xKey: string;
    yKey: string;
    xValue: any;
    yValue: any;
    first: boolean;
    last: boolean;
    min: boolean;
    max: boolean;
};
export declare function visibleRangeIndices(length: number, [range0, range1]: [number, number], xRange: (index: number) => [number, number] | undefined): [number, number];
export declare function getDatumRefPoint(series: ISeries<any, any, any>, datum: SeriesNodeDatum<unknown> & Pick<ErrorBoundSeriesNodeDatum, 'yBar'>, movedBounds: BBoxValues | undefined): {
    canvasX: number;
    canvasY: number;
} | undefined;
/**
 * Counts the number of items that match a condition within a specified range, starting at a given index and expanding
 * outwards until a certain count is reached.
 *
 * @param {number} min - The minimum number in the range.
 * @param {number} max - The maximum number in the range.
 * @param {number} start - The index at which to centre the search.
 * @param {number} countUntil - The maximum number until which to count.
 * @param {function(number): boolean} iteratee - A function that takes an index and returns a boolean to indicate if the value should be counted.
 * @returns {number} The count of items that matched the condition of the iteratee.
 */
export declare function countExpandingSearch(min: number, max: number, start: number, countUntil: number, iteratee: (index: number) => boolean): number;
