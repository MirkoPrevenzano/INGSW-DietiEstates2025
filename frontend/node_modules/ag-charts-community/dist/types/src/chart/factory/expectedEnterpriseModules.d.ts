import type { ChartType } from './chartTypes';
type EnterpriseModuleStub = {
    packageType?: 'enterprise';
    identifier?: string;
    chartTypes: ChartType[];
    useCount?: number;
    optionsInnerKey?: string;
    community?: boolean;
} & ({
    type: 'axis' | 'axis-option' | 'series' | 'series-option' | 'root' | 'legend';
    optionsKey: string;
} | {
    type: 'context';
    contextKey: string;
});
export declare const EXPECTED_ENTERPRISE_MODULES: EnterpriseModuleStub[];
export declare function isEnterpriseSeriesType(type: string): boolean;
export declare function isEnterpriseCartesian(seriesType: string): boolean;
export declare function isEnterprisePolar(seriesType: string): boolean;
export declare function isEnterpriseHierarchy(seriesType: string): boolean;
export declare function isEnterpriseTopology(seriesType: string): boolean;
export declare function isEnterpriseFlowProportion(seriesType: string): boolean;
export declare function isEnterpriseStandalone(seriesType: string): boolean;
export declare function isEnterpriseGauge(seriesType: string): boolean;
type UnknownPackage = {
    packageType: string;
} | EnterpriseModuleStub;
export declare function verifyIfModuleExpected(module: UnknownPackage): boolean;
export declare function getUnusedExpectedModules(): EnterpriseModuleStub[];
export {};
