export declare class DestroyFns {
    private destroyFns;
    destroy(): void;
    setFns(destroyFns: (() => void)[]): void;
    push(...destroyFns: (() => void)[]): void;
}
