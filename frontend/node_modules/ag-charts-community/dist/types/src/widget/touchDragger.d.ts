export type TouchDragCallbacks = {
    touchmove: (event: TouchEvent, touch: Touch) => void;
    touchend: (event: TouchEvent, touch: Touch) => void;
};
export declare class TouchDragger {
    private readonly glob;
    private readonly self;
    private readonly initialTouch;
    private readonly target;
    private readonly longtapTimer;
    private longTapInterrupted;
    constructor(glob: {
        globalTouchDragCallbacks?: TouchDragCallbacks;
    }, self: {
        dragTouchEnabled: boolean;
        touchDragger?: TouchDragger;
    }, myCallbacks: TouchDragCallbacks, initialTouch: Touch, target: HTMLElement);
    destroy(): void;
    private findInitialFinger;
    private readonly longtap;
    private readonly touchmove;
    private readonly touchend;
}
type Arg = ConstructorParameters<typeof TouchDragger>;
export declare function startOneFingerTouch(glob: Arg[0], self: Arg[1], myCallbacks: Arg[2], initialTouch: Arg[3], target: Arg[4]): TouchDragger | undefined;
export {};
