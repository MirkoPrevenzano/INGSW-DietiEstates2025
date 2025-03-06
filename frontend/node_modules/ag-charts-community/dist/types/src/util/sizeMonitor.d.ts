export type Size = {
    width: number;
    height: number;
    pixelRatio: number;
};
type OnSizeChange = (size: Size, element: HTMLElement) => void;
export declare class SizeMonitor {
    private readonly elements;
    private resizeObserver;
    private pixelRatioObserver;
    private documentReady;
    private queuedObserveRequests;
    constructor();
    onLoad: EventListener;
    private destroy;
    private observeWindow;
    private checkPixelRatio;
    private checkSize;
    observe(element: HTMLElement, cb: OnSizeChange): void;
    unobserve(element: HTMLElement): void;
    removeFromQueue(element: HTMLElement): void;
}
export {};
