import type { AgChartOptions } from 'ag-charts-types';
export declare function removeUsedEnterpriseOptions<T extends Partial<AgChartOptions>>(options: T, silent?: boolean): void;
export declare function removeUnusedEnterpriseOptions<T extends Partial<AgChartOptions>>(options: T): void;
