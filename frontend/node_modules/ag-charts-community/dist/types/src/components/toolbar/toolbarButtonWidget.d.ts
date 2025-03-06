import type { AgIconName } from 'ag-charts-types';
import type { LocaleManager } from '../../locale/localeManager';
import { ButtonWidget } from '../../widget/buttonWidget';
export interface ToolbarButtonWidgetOptions {
    icon?: AgIconName;
    label?: string;
    ariaLabel?: string;
    tooltip?: string;
}
export declare class ToolbarButtonWidget extends ButtonWidget {
    private readonly localeManager;
    section?: string;
    constructor(localeManager: LocaleManager);
    update(options: ToolbarButtonWidgetOptions): void;
    setChecked(checked: boolean): void;
}
