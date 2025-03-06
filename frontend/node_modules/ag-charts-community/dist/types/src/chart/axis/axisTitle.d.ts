import type { AgAxisCaptionFormatterParams, AgAxisCaptionOptions, FontStyle, FontWeight, Formatter, TextWrap } from 'ag-charts-types';
import { BaseProperties } from '../../util/properties';
import { Caption } from '../caption';
export declare class AxisTitle extends BaseProperties implements AgAxisCaptionOptions {
    readonly caption: Caption;
    enabled: boolean;
    text?: string;
    spacing?: number;
    fontStyle?: FontStyle;
    fontWeight?: FontWeight;
    fontSize: number;
    fontFamily: string;
    color?: string;
    wrapping: TextWrap;
    formatter?: Formatter<AgAxisCaptionFormatterParams>;
}
