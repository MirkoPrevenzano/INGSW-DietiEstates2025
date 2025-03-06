import type { DataModel, DataModelOptions, UngroupedData } from './dataModel';
interface CachedDataItem<D extends object, K extends keyof D & string = keyof D & string> {
    ids: string[];
    opts: DataModelOptions<K, any>;
    data: D[];
    dataModel: DataModel<any, any, any>;
    processedData: UngroupedData<any> | undefined;
}
export type CachedData = CachedDataItem<any, any>[];
export declare function canReuseCachedData<D extends object, K extends keyof D & string = keyof D & string>(cachedDataItem: CachedDataItem<any, any>, data: D[], ids: string[], opts: DataModelOptions<K, any>): boolean;
export {};
