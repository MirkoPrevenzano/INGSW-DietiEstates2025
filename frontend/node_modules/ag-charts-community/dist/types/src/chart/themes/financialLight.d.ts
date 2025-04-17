import type { AgChartThemeParams, WithThemeParams } from 'ag-charts-types';
import { ChartTheme } from './chartTheme';
export declare class FinancialLight extends ChartTheme {
    getDefaultColors(): {
        fills: {
            GREEN: string;
            RED: string;
            BLUE: string;
            GRAY: string;
        };
        fillsFallback: string[];
        strokes: {
            GREEN: string;
            RED: string;
            BLUE: string;
            GRAY: string;
        };
        sequentialColors: Record<string | number, string[]>;
        divergingColors: string[];
        up: {
            fill: string;
            stroke: string;
        };
        down: {
            fill: string;
            stroke: string;
        };
        neutral: {
            fill: string;
            stroke: string;
        };
        altUp: {
            fill: string;
            stroke: string;
        };
        altDown: {
            fill: string;
            stroke: string;
        };
        altNeutral: {
            fill: string;
            stroke: string;
        };
        hierarchyColors: string[];
        secondSequentialColors: string[];
        secondDivergingColors: string[];
        secondHierarchyColors: string[];
    };
    getPublicParameters(): Required<WithThemeParams<AgChartThemeParams>>;
    getTemplateParameters(): Map<any, any>;
}
