import type { AxisModule } from './axisModule';
import type { AxisOptionModule } from './axisOptionModule';
import type { ModuleInstance } from './baseModule';
import type { ContextModule, LegendModule, RootModule, SeriesModule } from './coreModules';
import type { SeriesOptionModule } from './optionsModuleTypes';
export type Module<M extends ModuleInstance = ModuleInstance> = ContextModule<M> | RootModule<M> | AxisModule | AxisOptionModule | LegendModule | SeriesModule<any, any> | SeriesOptionModule;
export declare abstract class BaseModuleInstance {
    protected readonly destroyFns: (() => void)[];
    destroy(): void;
}
export declare class ModuleRegistry {
    readonly modules: Module[];
    private readonly dependencies;
    private readonly modulesByOptionKey;
    register(...modules: Module[]): void;
    hasEnterpriseModules(): boolean;
    byType<T extends Module>(...types: T['type'][]): Generator<T>;
    private registerDependencies;
}
export declare const moduleRegistry: ModuleRegistry;
