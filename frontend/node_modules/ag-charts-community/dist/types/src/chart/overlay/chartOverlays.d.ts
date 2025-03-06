import type { LocaleManager } from '../../locale/localeManager';
import type { BBox } from '../../scene/bbox';
import { BaseProperties } from '../../util/properties';
import { Overlay } from './overlay';
export declare class ChartOverlays extends BaseProperties {
    darkTheme: boolean;
    readonly loading: Overlay;
    readonly noData: Overlay;
    readonly noVisibleSeries: Overlay;
    readonly unsupportedBrowser: Overlay;
    getFocusInfo(localeManager: LocaleManager): {
        text: string;
        rect: BBox;
    } | undefined;
    destroy(): void;
}
