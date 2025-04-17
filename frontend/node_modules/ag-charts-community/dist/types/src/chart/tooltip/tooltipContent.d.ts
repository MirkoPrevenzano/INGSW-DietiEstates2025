import type { AgTooltipMode } from 'ag-charts-types';
import type { LocaleManager } from '../../locale/localeManager';
import { type LegendSymbolOptions } from '../legend/legendSymbol';
export declare const DEFAULT_TOOLTIP_CLASS = "ag-charts-tooltip";
export declare const DEFAULT_TOOLTIP_DARK_CLASS = "ag-charts-tooltip--dark";
export type TooltipContentDataRow = {
    label: string;
    fallbackLabel?: string;
    value: string;
} | {
    label: undefined;
    fallbackLabel: string;
    value: string;
};
export type TooltipStructuredContent = {
    heading?: string;
    title?: string;
    symbol?: LegendSymbolOptions;
    data?: TooltipContentDataRow[];
};
export type TooltipContent = ({
    type: 'structured';
} & TooltipStructuredContent) | {
    type: 'raw';
    rawHtmlString: string;
};
export interface TooltipPaginationState {
    index: number;
    length: number;
}
export declare function tooltipContentAriaLabel(ungroupedContent: TooltipContent[]): string;
export declare function tooltipHtml(localeManager: LocaleManager | undefined, content: TooltipContent[], mode: AgTooltipMode, pagination: TooltipPaginationState | undefined): string | undefined;
