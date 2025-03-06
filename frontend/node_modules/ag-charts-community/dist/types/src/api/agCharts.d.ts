import type { AgChartInstance, AgChartOptions, AgFinancialChartOptions, AgGaugeOptions, AgSparklineOptions } from 'ag-charts-types';
import { type ChartInternalOptionMetadata } from '../module/optionsModule';
/**
 * Factory for creating and updating instances of AgChartInstance.
 *
 * @docsInterface
 */
export declare abstract class AgCharts {
    private static licenseManager?;
    private static licenseChecked;
    private static licenseCheck;
    /** @private - for use by Charts website dark-mode support. */
    static readonly optionsMutationFn?: (opts: AgChartOptions, preset?: string) => AgChartOptions;
    static getLicenseDetails(licenseKey: string): object | undefined;
    /**
     * Returns the `AgChartInstance` for a DOM node, if there is one.
     */
    static getInstance(element: HTMLElement): AgChartInstance | undefined;
    /**
     * Create a new `AgChartInstance` based upon the given configuration options.
     */
    static create<O extends AgChartOptions>(userOptions: O, optionsMetadata?: ChartInternalOptionMetadata): AgChartInstance<O>;
    static createFinancialChart(options: AgFinancialChartOptions): AgChartInstance<AgFinancialChartOptions>;
    static createGauge(options: AgGaugeOptions): AgChartInstance<AgGaugeOptions>;
    static __createSparkline(options: AgSparklineOptions): AgChartInstance<AgSparklineOptions>;
}
