import type { ModuleContext } from '../../module/moduleContext';
import { BBox } from '../../scene/bbox';
import type { BBoxValues } from '../../util/bboxinterface';
import type { PopoverOptions } from '../popover/popover';
import { BaseToolbar, type ToolbarButtonOptions, type ToolbarEventMap } from './toolbar';
import type { ToolbarButtonWidget } from './toolbarButtonWidget';
export interface FloatingToolbarAnchor {
    x: number;
    y: number;
    position?: 'above' | 'above-left' | 'right' | 'below';
}
interface FloatingToolbarEventMap<ButtonOptions extends ToolbarButtonOptions> extends ToolbarEventMap<ButtonOptions> {
    'toolbar-moved': {
        buttonBounds: Array<BBoxValues>;
        popoverBounds: BBoxValues;
    };
}
export declare abstract class FloatingToolbar<ButtonOptions extends ToolbarButtonOptions, ButtonWidget extends ToolbarButtonWidget> extends BaseToolbar<ButtonOptions, ButtonWidget, FloatingToolbarEventMap<ButtonOptions>> {
    protected hasPrefix: boolean;
    private readonly popover;
    private popoverBounds?;
    private readonly dragHandle;
    constructor(ctx: ModuleContext, id: string);
    show(options?: PopoverOptions): void;
    hide(): void;
    setAnchor(anchor: FloatingToolbarAnchor): void;
    hasBeenDragged(): boolean;
    ignorePointerEvents(): void;
    capturePointerEvents(): void;
    private onPopoverMoved;
    protected getButtonWidgetBounds(buttonWidget: ButtonWidget): BBox;
}
export {};
