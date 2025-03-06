type KeyAction = {
    name: KeyActionName;
    activatesFocusIndicator: boolean;
};
type KeyActionName = 'arrowdown' | 'arrowleft' | 'arrowright' | 'arrowup' | 'delete' | 'undo' | 'redo' | 'submit' | 'zoomin' | 'zoomout';
export declare function mapKeyboardEventToAction(event: KeyboardEvent): KeyAction | undefined;
export {};
