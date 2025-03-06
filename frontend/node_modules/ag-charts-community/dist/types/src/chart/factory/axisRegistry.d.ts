import type { AxisModule } from '../../module/axisModule';
import type { ModuleContext } from '../../module/moduleContext';
import type { ChartAxis } from '../chartAxis';
declare class AxisRegistry {
    private readonly axesMap;
    private readonly themeTemplates;
    register(axisType: string, module: Pick<AxisModule, 'moduleFactory' | 'themeTemplate'>): void;
    create(axisType: string, moduleContext: ModuleContext): ChartAxis;
    has(axisType: string): boolean;
    keys(): IterableIterator<string>;
    setThemeTemplate(axisType: string, themeTemplate: object): this;
    getThemeTemplate(axisType: string): object | undefined;
}
export declare const axisRegistry: AxisRegistry;
export {};
