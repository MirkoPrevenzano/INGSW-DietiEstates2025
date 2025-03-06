import type { ScaleType } from '../../scale/scale';
import { type DatumPropertyDefinition, type GroupValueProcessorDefinition, type ProcessedData, type ProcessorOutputPropertyDefinition, type PropertyValueProcessorDefinition, type ReducerOutputPropertyDefinition } from './dataModel';
export declare function keyProperty<K>(propName: K, scaleType?: ScaleType, opts?: Partial<DatumPropertyDefinition<K>>): DatumPropertyDefinition<K>;
export declare function valueProperty<K>(propName: K, scaleType?: ScaleType, opts?: Partial<DatumPropertyDefinition<K>>): DatumPropertyDefinition<K>;
export declare function rowCountProperty<K>(propName: K, opts?: Partial<DatumPropertyDefinition<K>>): DatumPropertyDefinition<K>;
export declare function rangedValueProperty<K>(propName: K, opts?: Partial<DatumPropertyDefinition<K>> & {
    min?: number;
    max?: number;
}): DatumPropertyDefinition<K>;
export declare function accumulativeValueProperty<K>(propName: K, scaleType?: ScaleType, opts?: Partial<DatumPropertyDefinition<K>> & {
    onlyPositive?: boolean;
}): DatumPropertyDefinition<K>;
export declare function trailingAccumulatedValueProperty<K>(propName: K, scaleType?: ScaleType, opts?: Partial<DatumPropertyDefinition<K>>): DatumPropertyDefinition<K>;
export declare function groupAccumulativeValueProperty<K>(propName: K, mode: 'normal' | 'trailing' | 'window' | 'window-trailing', sum: 'current' | 'last', opts: Partial<DatumPropertyDefinition<K>> & {
    rangeId?: string;
    groupId: string;
}, scaleType?: ScaleType): (GroupValueProcessorDefinition<any, any> | import("./dataModel").AggregatePropertyDefinition<any, any> | DatumPropertyDefinition<K>)[];
export declare function groupStackValueProperty<K>(propName: K, scaleType: ScaleType | undefined, opts: Partial<DatumPropertyDefinition<K>> & {
    rangeId?: string;
    groupId: string;
}): (GroupValueProcessorDefinition<any, any> | DatumPropertyDefinition<K>)[];
export declare const SMALLEST_KEY_INTERVAL: ReducerOutputPropertyDefinition<'smallestKeyInterval'>;
export declare const LARGEST_KEY_INTERVAL: ReducerOutputPropertyDefinition<'largestKeyInterval'>;
export declare const SORT_DOMAIN_GROUPS: ProcessorOutputPropertyDefinition<'sortedGroupDomain'>;
export declare function normaliseGroupTo(matchGroupIds: string[], normaliseTo: number): GroupValueProcessorDefinition<any, any>;
export declare function normalisePropertyTo(property: string, normaliseTo: [number, number], zeroDomain: number, rangeMin?: number, rangeMax?: number): PropertyValueProcessorDefinition<any>;
export declare function animationValidation(valueKeyIds?: string[]): ProcessorOutputPropertyDefinition;
export declare function accumulateGroup(matchGroupId: string, mode: 'normal' | 'trailing' | 'window' | 'window-trailing', sum: 'current' | 'last', separateNegative?: boolean): GroupValueProcessorDefinition<any, any>;
export declare function accumulateStack(matchGroupId: string): GroupValueProcessorDefinition<any, any>;
export declare function diff(id: string, previousData: ProcessedData<any>, updateMovedData?: boolean): ProcessorOutputPropertyDefinition<'diff'>;
type KeyType = string | number | boolean | object;
export declare function createDatumId(keys: KeyType | KeyType[], ...extraKeys: (string | number | boolean)[]): any;
export {};
