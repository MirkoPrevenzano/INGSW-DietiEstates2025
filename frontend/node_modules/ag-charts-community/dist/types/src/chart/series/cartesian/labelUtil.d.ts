import type { Point } from '../../../scene/point';
import type { Text } from '../../../scene/shape/text';
import type { Label } from '../../label';
type Bounds = {
    x: number;
    y: number;
    width: number;
    height: number;
};
export type BarLabelPlacement = 'inside-center' | 'inside-start' | 'inside-end' | 'outside-start' | 'outside-end';
type LabelDatum = Point & {
    text: string;
    textAlign: CanvasTextAlign;
    textBaseline: CanvasTextBaseline;
};
export declare function updateLabelNode(textNode: Text, label: Label, labelDatum?: LabelDatum): void;
export declare function adjustLabelPlacement({ isUpward, isVertical, placement, padding, rect, }: {
    placement: BarLabelPlacement;
    isUpward: boolean;
    isVertical: boolean;
    padding?: number;
    rect: Bounds;
}): Omit<LabelDatum, 'text'>;
export {};
