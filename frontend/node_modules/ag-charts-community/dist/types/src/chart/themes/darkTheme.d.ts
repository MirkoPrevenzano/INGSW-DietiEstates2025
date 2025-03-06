import type { AgChartThemeOptions, AgChartThemeParams, WithThemeParams } from 'ag-charts-types';
import { ChartTheme } from './chartTheme';
import type { DefaultColors } from './defaultColors';
export declare class DarkTheme extends ChartTheme {
    getDefaultColors(): DefaultColors;
    getPublicParameters(): Required<WithThemeParams<AgChartThemeParams>>;
    getTemplateParameters(): Map<any, any>;
    constructor(options?: AgChartThemeOptions);
}
