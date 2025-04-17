import type { Operation } from 'ag-charts-types';
export { getChartTheme } from './chart/mapping/themes';
export * as themeSymbols from './chart/themes/symbols';
export declare const themeNames: string[];
export { ChartTheme } from './chart/themes/chartTheme';
export { themes } from './chart/mapping/themes';
export * from './chart/themes/symbols';
export declare function resolveOperation(operation: Operation): any;
