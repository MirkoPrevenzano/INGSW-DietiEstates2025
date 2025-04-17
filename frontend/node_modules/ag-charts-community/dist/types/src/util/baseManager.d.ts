import { Listeners } from './listeners';
export declare abstract class BaseManager<EventType extends string = never, Event extends {
    type: any;
} = never> {
    protected readonly listeners: Listeners<EventType, (event: Event) => void>;
    protected readonly destroyFns: (() => void)[];
    protected destroyed: boolean;
    addListener<T extends EventType>(type: T, handler: (event: Event & {
        type: T;
    }) => void): () => void;
    destroy(): void;
}
