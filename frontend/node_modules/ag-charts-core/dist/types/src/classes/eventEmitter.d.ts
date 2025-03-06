export type EventListener<T> = (event: T) => void;
export declare class EventEmitter<EventMap extends object> {
    private readonly events;
    /**
     * Registers an event listener.
     * @param eventName The event name to listen for.
     * @param listener The callback to be invoked on the event.
     * @returns A function to unregister the listener.
     */
    on<K extends keyof EventMap>(eventName: K, listener: EventListener<EventMap[K]>): () => void;
    /**
     * Unregisters an event listener.
     * @param eventName The event name to stop listening for.
     * @param listener The callback to be removed.
     */
    off<K extends keyof EventMap>(eventName: K, listener: EventListener<EventMap[K]>): void;
    /**
     * Emits an event to all registered listeners.
     * @param eventName The name of the event to emit.
     * @param event The event payload.
     */
    emit<K extends keyof EventMap>(eventName: K, event: EventMap[K]): void;
    /**
     * Clears all listeners for a specific event or all events if no event name is provided.
     * @param eventName (Optional) The name of the event to clear listeners for. If not provided, all listeners for all events are cleared.
     */
    clear<K extends keyof EventMap>(eventName?: K): void;
}
