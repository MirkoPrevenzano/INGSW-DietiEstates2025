import type { Scale } from '../../scale/scale';
import type { BBox } from '../../scene/bbox';
import type { Path } from '../../scene/shape/path';
import type { DataController } from '../data/dataController';
import type { DataModel, DataModelOptions, ProcessedData } from '../data/dataModel';
import type { PickFocusInputs, PickFocusOutputs, SeriesConstructorOpts, SeriesNodeDataContext } from './series';
import { Series } from './series';
import type { SeriesProperties } from './seriesProperties';
import type { SeriesNodeDatum } from './seriesTypes';
export interface DataModelSeriesNodeDatum extends SeriesNodeDatum<number> {
}
export interface DataModelSeriesNodeDataContext<TDatum, TLabel = TDatum> extends SeriesNodeDataContext<number, TDatum, TLabel> {
}
export type DataModelSeriesConstructorOpts<TProps extends SeriesProperties<any>> = SeriesConstructorOpts<TProps> & {
    categoryKey: string | undefined;
    clipFocusBox?: boolean;
};
export declare abstract class DataModelSeries<TDatum extends SeriesNodeDatum<number>, TProps extends SeriesProperties<any>, TLabel = TDatum, TContext extends DataModelSeriesNodeDataContext<TDatum, TLabel> = DataModelSeriesNodeDataContext<TDatum, TLabel>> extends Series<number, TDatum, TProps, TLabel, TContext> {
    protected dataModel?: DataModel<any, any, any>;
    protected processedData?: ProcessedData<any>;
    private readonly categoryKey;
    private readonly clipFocusBox;
    protected constructor({ clipFocusBox, categoryKey, ...seriesOpts }: DataModelSeriesConstructorOpts<TProps>);
    protected getScaleInformation({ xScale, yScale, }: {
        xScale?: Scale<any, any, any>;
        yScale?: Scale<any, any, any>;
    }): {
        isContinuousX: boolean;
        isContinuousY: boolean;
        xScaleType: import("../../scale/scale").ScaleType | undefined;
        yScaleType: import("../../scale/scale").ScaleType | undefined;
    };
    private getModulePropertyDefinitions;
    protected requestDataModel<D extends object, K extends keyof D & string = keyof D & string, G extends boolean | undefined = undefined>(dataController: DataController, data: D[] | undefined, opts: DataModelOptions<K, boolean | undefined, false>): Promise<{
        dataModel: DataModel<D, K, G>;
        processedData: ProcessedData<D>;
    }>;
    protected isProcessedDataAnimatable(): boolean;
    protected checkProcessedDataAnimatable(): void;
    protected abstract computeFocusBounds(opts: PickFocusInputs): Path | BBox | undefined;
    abstract getNodeData(): TDatum[] | undefined;
    pickFocus(opts: PickFocusInputs): PickFocusOutputs | undefined;
    protected isDatumEnabled(nodeData: TDatum[], datumIndex: number): boolean;
    private computeFocusDatumIndex;
    protected keysOrValues(xKey: string): any[];
    protected getCategoryKey(): string | undefined;
    getCategoryValue(datumIndex: number): any;
    datumIndexForCategoryValue(categoryValue: any): number | undefined;
}
