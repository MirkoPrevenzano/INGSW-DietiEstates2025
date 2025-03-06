import type { DeepRequired } from 'ag-charts-core';
import type { AgTouchOptions } from 'ag-charts-types';
import { BaseProperties } from '../util/properties';
type OptionsImp = DeepRequired<AgTouchOptions>;
export declare class Touch extends BaseProperties implements OptionsImp {
    dragAction: OptionsImp['dragAction'];
}
export {};
