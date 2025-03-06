import type { LocaleManager } from '../../locale/localeManager';
import type { BBox } from '../../scene/bbox';
import { BaseProperties } from '../../util/properties';
import type { AnimationManager } from '../interaction/animationManager';
export declare const DEFAULT_OVERLAY_CLASS = "ag-charts-overlay";
export declare const DEFAULT_OVERLAY_DARK_CLASS = "ag-charts-dark-overlay";
export declare class Overlay extends BaseProperties {
    protected className: string;
    protected defaultMessageId: string;
    enabled: boolean;
    text?: string;
    renderer?: () => string | HTMLElement;
    private content?;
    focusBox?: BBox;
    constructor(className: string, defaultMessageId: string);
    getText(localeManager: LocaleManager): string;
    getElement(animationManager: AnimationManager | undefined, localeManager: LocaleManager, rect: BBox): HTMLElement;
    removeElement(cleanup?: () => void | undefined, animationManager?: AnimationManager): void;
}
