import { BBox } from '../../scene/bbox';
import type { Padding } from '../../util/padding';
import type { ISeries } from '../series/seriesTypes';
export declare class SeriesLabelLayoutManager {
    private readonly labelData;
    updateLabels(placedLabelSeries: ISeries<unknown, unknown, unknown>[], padding: Padding, seriesRect?: BBox): void;
}
