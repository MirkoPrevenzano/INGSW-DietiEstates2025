export declare class Pool<T, P> {
    private readonly name;
    private readonly buildItem;
    private readonly releaseItem;
    private readonly destroyItem;
    private readonly maxPoolSize;
    private readonly cleanupTimeMs;
    private static readonly pools;
    static getPool<T, P>(name: string, buildItem: (p: P) => T, releaseItem: (i: T) => void, destroyItem: (i: T) => void, maxPoolSize: number): Pool<T, P>;
    static readonly debug: import("./debug").DebugLogger;
    private readonly freePool;
    private readonly busyPool;
    private cleanPoolTimer?;
    private cleanPoolDue?;
    constructor(name: string, buildItem: (params: P) => T, releaseItem: (item: T) => void, destroyItem: (item: T) => void, maxPoolSize: number, cleanupTimeMs?: number);
    isFull(): boolean;
    hasFree(): boolean;
    obtain(params: P): {
        item: T;
        release: () => void;
    };
    obtainFree(): {
        item: NonNullable<T>;
        release: () => void;
    };
    release(item: T): void;
    private cleanPool;
    destroy(): void;
}
