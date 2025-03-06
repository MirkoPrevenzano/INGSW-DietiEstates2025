import type { Point, SizedPoint } from '../point';
export type LabelPlacement = 'top' | 'bottom' | 'left' | 'right' | 'top-left' | 'top-right' | 'bottom-left' | 'bottom-right';
export interface MeasuredLabel {
    readonly text: string;
    readonly width: number;
    readonly height: number;
}
export interface PlacedLabelDatum {
    readonly point: Point;
    readonly label: MeasuredLabel;
}
export interface PointLabelDatum {
    readonly point: Readonly<SizedPoint>;
    readonly label: MeasuredLabel;
    readonly anchor: Point | undefined;
    readonly placement: LabelPlacement | undefined;
}
export interface PlacedLabel<PLD = PointLabelDatum> extends MeasuredLabel, Readonly<Point> {
    readonly index: number;
    readonly datum: PLD;
}
interface LabelBounds extends Readonly<Point> {
    readonly width: number;
    readonly height: number;
}
export declare function isPointLabelDatum(x: any): x is PointLabelDatum;
/**
 * @param data Points and labels for one or more series. The order of series determines label placement precedence.
 * @param bounds Bounds to fit the labels into. If a label can't be fully contained, it doesn't fit.
 * @param padding
 * @returns Placed labels for all series.
 */
export declare function placeLabels(data: Map<string, PointLabelDatum[]>, bounds?: LabelBounds, padding?: number): Map<string, PlacedLabel<PointLabelDatum>[]>;
export declare function axisLabelsOverlap(data: readonly PlacedLabelDatum[], padding?: number): boolean;
export {};
