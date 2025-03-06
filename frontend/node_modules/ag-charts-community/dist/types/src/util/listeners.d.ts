type Handler = (...args: any[]) => void;
export type Listener<H extends Handler> = {
    symbol?: symbol;
    handler: H;
};
export declare class Listeners<EventType extends string, EventHandler extends Handler> {
    protected readonly registeredListeners: Map<EventType, Listener<EventHandler>[]>;
    addListener<T extends EventType>(eventType: T, handler: EventHandler): () => void;
    private removeListener;
    dispatch(eventType: EventType, ...params: Parameters<EventHandler>): void;
    protected getListenersByType(eventType: EventType): Listener<EventHandler>[];
    destroy(): void;
}
export {};
