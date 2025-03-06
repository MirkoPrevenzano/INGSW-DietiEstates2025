import type { LabelIcon } from '../../dom/elements';
import { type MenuCloser } from '../../util/keynavUtil';
import { AnchoredPopover, type AnchoredPopoverOptions } from '../popover/anchoredPopover';
export interface MenuOptions<Value = any> extends AnchoredPopoverOptions {
    items: Array<MenuItem<Value>>;
    sourceEvent: Event;
    value?: Value;
    onPress?: (item: MenuItem<Value>) => void;
    menuItemRole?: 'menuitem' | 'menuitemradio';
}
export type MenuItem<Value = any> = LabelIcon & {
    value: Value;
    strokeWidth?: number;
};
/**
 * An anchored popover containing a list of pressable items.
 */
export declare class Menu extends AnchoredPopover {
    protected menuCloser?: MenuCloser;
    show<Value = any>(options: MenuOptions<Value>): void;
    private createRow;
}
