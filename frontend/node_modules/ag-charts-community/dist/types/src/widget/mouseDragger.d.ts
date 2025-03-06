export type MouseDragCallbacks = {
    mousedown: (event: MouseEvent) => void;
    mousemove: (event: MouseEvent) => void;
    mouseup: (event: MouseEvent) => void;
};
export declare class MouseDragger {
    private readonly glob;
    private readonly self;
    private readonly window;
    constructor(glob: {
        globalMouseDragCallbacks?: MouseDragCallbacks;
    }, self: {
        mouseDragger?: MouseDragger;
    }, myCallbacks: MouseDragCallbacks, downEvent: MouseEvent);
    destroy(): void;
    private readonly mousegeneral;
    private readonly mousemove;
    private readonly mouseup;
}
type Arg = ConstructorParameters<typeof MouseDragger>;
export declare function startMouseDrag(glob: Arg[0], self: Arg[1], myCallbacks: Arg[2], downEvent: Arg[3]): MouseDragger | undefined;
export {};
