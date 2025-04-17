import { type OptionsDefs } from 'ag-charts-core';
import type { AgChartTheme, AgChartThemeName } from 'ag-charts-types';
import { ChartTheme } from '../themes/chartTheme';
type SpecialThemeName = 'ag-financial' | 'ag-financial-dark';
type ThemeMap = {
    [key in AgChartThemeName | SpecialThemeName | 'undefined' | 'null']?: () => ChartTheme;
};
export declare const themes: ThemeMap;
export declare const getChartTheme: (value: unknown) => ChartTheme;
export declare const themeOptionsDef: OptionsDefs<AgChartTheme>;
export {};
