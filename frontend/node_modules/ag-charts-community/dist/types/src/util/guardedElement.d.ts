export declare class GuardedElement {
    private readonly element;
    private readonly topTabGuard;
    private readonly bottomTabGuard;
    private readonly destroyFns;
    private guardTabIndex;
    private hasFocus;
    constructor(element: HTMLElement, topTabGuard: HTMLElement, bottomTabGuard: HTMLElement);
    set tabIndex(index: number);
    destroy(): void;
    private initTabGuard;
    private setGuardIndices;
    private onFocus;
    private onBlur;
    private onTab;
    private static queryFocusable;
    private findEnterTarget;
    private findExitTarget;
    private static findBeforeAndAfter;
}
