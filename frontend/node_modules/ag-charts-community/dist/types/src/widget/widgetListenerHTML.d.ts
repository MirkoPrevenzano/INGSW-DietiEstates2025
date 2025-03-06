import type { WidgetEventMap_HTML } from './widgetEvents';
type EventMap = WidgetEventMap_HTML;
type EventType = keyof WidgetEventMap_HTML;
type Targetable = {
    getElement(): HTMLElement;
};
type Handler<T, K extends EventType> = (event: EventMap[K], current: T) => unknown;
export declare class WidgetListenerHTML {
    private widgetListeners?;
    private sourceListeners?;
    private initSourceHandler;
    private lazyGetWidgetListeners;
    add<T extends Targetable, K extends EventType>(type: K, target: T, handler: Handler<T, K>): void;
    remove<T extends Targetable, K extends EventType>(type: K, target: T, handler: Handler<T, K>): void;
    destroy<T extends Targetable>(target: T): void;
}
export {};
