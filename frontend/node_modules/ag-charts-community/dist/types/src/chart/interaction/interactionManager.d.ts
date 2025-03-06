export declare enum InteractionState {
    Default = 32,
    ZoomDrag = 16,
    Annotations = 8,
    ContextMenu = 4,
    Animation = 2,
    AnnotationsSelected = 1,
    Clickable = 41,
    Focusable = 34,
    Keyable = 43,
    ContextMenuable = 36,// AG-10233
    AnnotationsMoveable = 9,
    AnnotationsDraggable = 57,
    ZoomDraggable = 50,
    ZoomClickable = 34,
    ZoomWheelable = 59,
    All = 63
}
export declare class InteractionManager {
    private stateQueue;
    pushState(state: InteractionState): void;
    popState(state: InteractionState): void;
    isState(allowedStates: InteractionState): boolean;
}
