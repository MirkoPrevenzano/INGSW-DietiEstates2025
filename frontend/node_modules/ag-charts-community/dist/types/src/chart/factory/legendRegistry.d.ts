import type { LegendFactory } from '../../module/coreModules';
import type { ModuleContext } from '../../module/moduleContext';
import type { ChartLegendType } from '../legend/legendDatum';
interface LegendRegistryRecord {
    optionsKey: string;
    moduleFactory: LegendFactory;
}
type LegendRegistryOptions = LegendRegistryRecord & {
    themeTemplate?: object;
};
declare class LegendRegistry {
    private readonly legendMap;
    private readonly themeTemplates;
    register(legendType: ChartLegendType, { optionsKey, moduleFactory, themeTemplate }: LegendRegistryOptions): void;
    create(legendType: ChartLegendType, moduleContext: ModuleContext): import("../legend/legendDatum").ChartLegend;
    getThemeTemplates(): {
        [k: string]: object | undefined;
    };
    getKeys(): Record<ChartLegendType, string>;
}
export declare const legendRegistry: LegendRegistry;
export {};
