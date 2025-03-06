import type { ModuleInstance } from '../module/baseModule';
import type { LegendModule, RootModule } from '../module/coreModules';
import type { ModuleContext } from '../module/moduleContext';
import { ModuleMap } from '../module/moduleMap';
import type { ChartLegend } from './legend/legendDatum';
export declare class ModulesManager extends ModuleMap<RootModule | LegendModule, ModuleInstance, ModuleContext> {
    applyOptions(options: object): void;
    legends(): Generator<{
        legendType: import("./legend/legendDatum").ChartLegendType;
        legend: ChartLegend;
    }, void, unknown>;
}
