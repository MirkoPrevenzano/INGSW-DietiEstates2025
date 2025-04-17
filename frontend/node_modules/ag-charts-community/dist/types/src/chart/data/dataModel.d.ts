import type { ChartMode } from '../chartMode';
import { RangeLookup } from './rangeLookup';
export interface ScopeProvider {
    id: string;
}
export interface DataGroup {
    keys: any[];
    datumIndices: number[][];
    aggregation: any[][];
    validScopes: Set<ScopeId>;
}
export interface UngroupedDataItem<I, D, V> {
    index: I;
    keys: any[];
    values: V;
    aggValues?: [number, number][];
    datum: D;
    validScopes?: Set<string>;
}
declare const DOMAIN_RANGES: unique symbol;
type ScopeId = string;
interface CommonMetadata<D> {
    input: {
        count: number;
    };
    scopes: Set<ScopeId>;
    dataSources: Map<ScopeId, unknown[]>;
    invalidKeys: Map<ScopeId, boolean[]> | undefined;
    invalidData: Map<ScopeId, boolean[]> | undefined;
    keys: Map<ScopeId, unknown[]>[];
    columns: any[][];
    columnScopes: Set<ScopeId>[];
    domain: {
        keys: any[][];
        values: any[][];
        groups?: any[][];
        aggValues?: [number, number][];
    };
    reduced?: {
        diff?: Record<string, ProcessedOutputDiff>;
        smallestKeyInterval?: number;
        largestKeyInterval?: number;
        sortedGroupDomain?: any[][];
        animationValidation?: {
            uniqueKeys: boolean;
            orderedKeys: boolean;
        };
    };
    defs: {
        keys: (Scoped & DatumPropertyDefinition<keyof D>)[];
        values: (Scoped & DatumPropertyDefinition<keyof D>)[];
        allScopesHaveSameDefs: boolean;
    };
    partialValidDataCount: number;
    time: number;
    [DOMAIN_RANGES]: Map<string, RangeLookup>;
}
export interface UngroupedData<D> extends CommonMetadata<D> {
    type: 'ungrouped';
    aggregation?: [number, number][][];
}
export interface GroupedData<D> extends CommonMetadata<D> {
    type: 'grouped';
    groups: DataGroup[];
}
export type ProcessedOutputDiff = {
    changed: boolean;
    added: Set<string>;
    updated: Set<string>;
    removed: Set<string>;
    moved: Set<string>;
};
export interface ProcessedDataDef {
    index: number;
    def: PropertyDefinition<any>;
}
export type ProcessedData<D> = UngroupedData<D> | GroupedData<D>;
export type DatumPropertyType = 'range' | 'category';
export declare function fixNumericExtent(extent: Array<number | Date> | null): [] | [number, number];
type MissMap = Map<string, number>;
export declare function getMissCount(scopeProvider: ScopeProvider, missMap: MissMap | undefined): number;
type GroupingFn<K> = (keys: unknown[]) => K[];
export type GroupByFn = (extractedData: UngroupedData<any>) => GroupingFn<any>;
export type DataModelOptions<K, Grouped extends boolean | undefined, IsScoped extends boolean = true> = {
    props: PropertyDefinition<K, IsScoped>[];
    groupByKeys?: Grouped;
    groupByData?: Grouped;
    groupByFn?: GroupByFn;
};
export type PropertyDefinition<K, IsScoped = false> = (DatumPropertyDefinition<K> & (IsScoped extends true ? Scoped : unknown)) | AggregatePropertyDefinition<any, any, any> | (PropertyValueProcessorDefinition<any> & (IsScoped extends true ? Scoped : unknown)) | GroupValueProcessorDefinition<any, any> | ReducerOutputPropertyDefinition<any> | ProcessorOutputPropertyDefinition<any>;
export type ProcessorFn = (datum: unknown, index: number) => unknown;
export type PropertyId<K extends string> = K | {
    id: string;
};
export type Scoped = {
    /** Scope(s) a property definition belongs to (typically the defining entities unique identifier). */
    scopes: ScopeId[];
};
type PropertyIdentifiers = {
    id?: string;
    /** Map<Scope, Set<Id>> */
    idsMap?: Map<string, Set<string>>;
    /** Optional group a property belongs to, for cross-scope combination. */
    groupId?: string;
};
type PropertySelectors = {
    /** Optional group a property belongs to, for cross-scope combination. */
    matchGroupIds?: string[];
};
export type DatumPropertyDefinition<K> = PropertyIdentifiers & {
    type: 'key' | 'value';
    valueType: DatumPropertyType;
    property: K;
    forceValue?: any;
    includeProperty?: boolean;
    invalidValue?: any;
    missing?: MissMap;
    missingValue?: any;
    separateNegative?: boolean;
    validation?: (value: any, datum: any, index: number) => boolean;
    processor?: () => ProcessorFn;
};
export type AggregatePropertyDefinition<D, K extends keyof D & string, R = [number, number], R2 = R> = Omit<PropertyIdentifiers, 'scopes'> & PropertySelectors & {
    type: 'aggregate';
    aggregateFunction: (values: D[K][], keys?: D[K][]) => R;
    groupAggregateFunction?: (next?: R, acc?: R2) => R2;
    finalFunction?: (result: R2) => [number, number];
};
type GroupValueAdjustFn<D, K extends keyof D & string> = (columns: D[K][][], indexes: number[], dataGroup: DataGroup) => void;
export type GroupValueProcessorDefinition<D, K extends keyof D & string> = PropertyIdentifiers & PropertySelectors & {
    type: 'group-value-processor';
    /**
     * Outer function called once per all data processing; inner function called once per group;
     * innermost called once per datum.
     */
    adjust: () => () => GroupValueAdjustFn<D, K>;
};
type PropertyValueAdjustFn<D> = (processedData: ProcessedData<D>, valueIndex: number) => void;
export type PropertyValueProcessorDefinition<D> = PropertyIdentifiers & {
    type: 'property-value-processor';
    property: string;
    adjust: () => PropertyValueAdjustFn<D>;
};
type ReducerOutputTypes = NonNullable<UngroupedData<any>['reduced']>;
type ReducerOutputKeys = keyof ReducerOutputTypes;
export type ReducerOutputPropertyDefinition<P extends ReducerOutputKeys = ReducerOutputKeys> = PropertyIdentifiers & {
    type: 'reducer';
    property: P;
    initialValue?: ReducerOutputTypes[P];
    reducer: () => (acc: ReducerOutputTypes[P], keys: unknown[]) => ReducerOutputTypes[P];
};
export type ProcessorOutputPropertyDefinition<P extends ReducerOutputKeys = ReducerOutputKeys> = PropertyIdentifiers & {
    type: 'processor';
    property: P;
    calculate: (data: ProcessedData<any>, previousValue: ReducerOutputTypes[P] | undefined) => ReducerOutputTypes[P];
};
export declare function datumKeys(keys: ProcessedData<unknown>['keys'], columnScope: ScopeId, datumIndex: number): any[] | undefined;
export declare function getPathComponents(path: string): string[] | undefined;
export declare class DataModel<D extends object, K extends keyof D & string = keyof D & string, Grouped extends boolean | undefined = undefined> {
    private readonly opts;
    private readonly mode;
    private readonly suppressFieldDotNotation;
    private readonly debug;
    private readonly scopeCache;
    private readonly keys;
    private readonly values;
    private readonly aggregates;
    private readonly groupProcessors;
    private readonly propertyProcessors;
    private readonly reducers;
    private readonly processors;
    constructor(opts: DataModelOptions<K, Grouped, true>, mode?: ChartMode, suppressFieldDotNotation?: boolean);
    resolveProcessedDataDefById(scope: ScopeProvider, searchId: string): ProcessedDataDef | never;
    resolveProcessedDataIndexById(scope: ScopeProvider, searchId: string): number;
    resolveKeysById<T = string>(scope: ScopeProvider, searchId: string, processedData: UngroupedData<any> | GroupedData<any>): T[];
    hasColumnById(scope: ScopeProvider, searchId: string): boolean;
    resolveColumnById<T = any>(scope: ScopeProvider, searchId: string, processedData: UngroupedData<any> | GroupedData<any>): T[];
    /**
     * Provides a convenience iterator to iterate over all of the extract datum values in a
     * specific DataGroup.
     *
     * @param scope to which datums should belong
     * @param group containing the datums
     * @param processedData containing the group
     */
    forEachDatum(scope: ScopeProvider, processedData: GroupedData<any>, group: DataGroup): Generator<any, void, unknown>;
    /**
     * Provides a convenience iterator to iterate over all of the extracted datum values in a
     * GroupedData.
     *
     * @param scope to which datums should belong
     * @param processedData to iterate through
     */
    forEachGroupDatum(scope: ScopeProvider, processedData: GroupedData<any>): Generator<{
        group: DataGroup;
        groupIndex: number;
        columnIndex: number;
        datumIndex: number;
        valueIndex: number;
    }, void, unknown>;
    /**
     * Provides a window-based convenience iterator to iterate over all of the extracted datum
     * values in a GroupedData, including the previous and next entries relative to each datum.
     *
     * @param scope to which datums should belong
     * @param processedData to iterate through
     */
    forEachGroupDatumTuple(scope: ScopeProvider, processedData: GroupedData<any>): Generator<{
        group: DataGroup;
        nextGroup?: DataGroup | undefined;
        nextGroupIndex?: number | undefined;
        columnIndex: number;
        groupIndex: number;
        datumIndexes: [number | undefined, number, number | undefined];
    }, void, unknown>;
    getDomain(scope: ScopeProvider, searchId: string, type: PropertyDefinition<any>['type'], processedData: ProcessedData<K>): any[] | [number, number] | [];
    getDomainBetweenRange(scope: ScopeProvider, searchIds: string[], [i0, i1]: [number, number], processedData: ProcessedData<K>): [number, number];
    private getDomainsByType;
    processData(sources: Map<string, unknown[]>): (Grouped extends true ? GroupedData<D> : UngroupedData<D>) | undefined;
    private warnDataMissingProperties;
    private processScopeCache;
    private valueGroupIdxLookup;
    private valueIdxLookup;
    private extractData;
    private extractKeys;
    private readonly markScopeDatumInvalid;
    private extractValues;
    private groupData;
    private aggregateUngroupedData;
    private aggregateGroupedData;
    private postProcessGroups;
    private postProcessProperties;
    private reduceData;
    private postProcessData;
    private initDataDomainProcessor;
    buildAccessors(defs: Iterable<{
        property: string;
    }>): Map<string, (d: any) => any>;
}
export {};
