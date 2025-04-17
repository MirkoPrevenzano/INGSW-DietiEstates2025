import type { Widget } from '../../widget/widget';
import type { MouseWidgetEvent, WidgetEventMap } from '../../widget/widgetEvents';
type TSythetic = 'click' | 'dblclick';
type SytheticMap<T extends TSythetic> = {
    mouse: {
        device: 'mouse';
    } & MouseWidgetEvent<T>;
    touch: {
        device: 'touch';
        sourceEvent: TouchEvent;
    } & Omit<MouseWidgetEvent<T>, 'sourceEvent'>;
};
type Device = keyof SytheticMap<TSythetic>;
type SyntheticEvent<D extends Device, T extends TSythetic> = SytheticMap<T>[D];
/**
 * A `DragInterpreterClickEvent` is either a native 'click' MouseEvent, or a sythetic click event fired by a single
 * finger 'touchstart' and 'touchend'.
 */
type MouseClick = SyntheticEvent<'mouse', 'click'>;
type TouchClick = SyntheticEvent<'touch', 'click'>;
export type DragInterpreterClickEvent = MouseClick | TouchClick;
/**
 * A `DragInterpreterDblClickEvent` is either a native 'dblclick' MouseEvent, or a sythetic click event fired by two
 * finger 'touchstart' and 'touchend' in quick succession (DOUBLE_TAP_TIMER_MS).
 */
type MouseDblClick = SyntheticEvent<'mouse', 'dblclick'>;
type TouchDblClick = SyntheticEvent<'touch', 'dblclick'>;
export type DragInterpreterDblClickEvent = MouseDblClick | TouchDblClick;
type Type = 'mousemove' | 'click' | 'dblclick' | 'drag-start' | 'drag-move' | 'drag-end';
type EventMap = Omit<WidgetEventMap, 'click' | 'dblclick'> & {
    click: DragInterpreterClickEvent;
    dblclick: DragInterpreterDblClickEvent;
};
/**
 * In the interest of robustness (and simplicity), the Widget class always dispatches these events after mousedown &
 * mouseup events for the left-button:
 *
 * -   One 'drag-start'
 * -   Zero or more 'drag-move'
 * -   One 'drag-end'
 * -   Zero or one 'click' (only dispatched if the mouseup event is on target).
 *
 * To distinguish between drag and click actions, use this class. It ensure that for each mousedown-mouseup pair, it
 * dispatches either a set of 'drag-*' events or a single 'click' event but not both.
 */
export declare class DragInterpreter {
    private readonly destroyFns;
    private readonly listeners;
    private dragStartEvent?;
    private isDragging;
    private lastClick?;
    private readonly touch;
    constructor(widget: Widget);
    destroy(): void;
    addListener<T extends Type>(type: T, handler: (e: EventMap[T]) => void): () => void;
    private dispatch;
    private onTouchStart;
    private onTouchMove;
    private onTouchEnd;
    private onMouseMove;
    private onDblClick;
    private onDragStart;
    private onDragMove;
    private onDragEnd;
}
export {};
