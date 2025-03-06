import type { NativeWidget } from '../../widget/nativeWidget';
import type { DragWidgetEvent } from '../../widget/widgetEvents';
import { Popover, type PopoverOptions } from './popover';
export declare abstract class DraggablePopover<Options extends PopoverOptions = PopoverOptions> extends Popover<Options> {
    abstract readonly dragHandleDraggingClass: string;
    protected dragged: boolean;
    private dragStartState?;
    setDragHandle(dragHandle: NativeWidget): void;
    protected onDragStart(event: DragWidgetEvent<'drag-start'>): void;
    protected onDragMove(event: DragWidgetEvent<'drag-move'>): void;
    protected onDragEnd(): void;
}
