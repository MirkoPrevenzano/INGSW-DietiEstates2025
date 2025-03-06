import type { LayoutContext } from '../module/baseModule';
import type { ChartOptions } from '../module/optionsModule';
import { BBox } from '../scene/bbox';
import { Padding } from '../util/padding';
import type { TransferableResources } from './chart';
import { Chart } from './chart';
export declare class PolarChart extends Chart {
    static readonly className = "PolarChart";
    static readonly type: "polar";
    padding: Padding;
    constructor(options: ChartOptions, resources?: TransferableResources);
    getChartType(): "polar";
    protected performLayout(ctx: LayoutContext): Promise<void>;
    protected updateAxes(seriesBox: BBox, cx: number, cy: number, radius: number): void;
    private computeCircle;
    private refineCircle;
}
