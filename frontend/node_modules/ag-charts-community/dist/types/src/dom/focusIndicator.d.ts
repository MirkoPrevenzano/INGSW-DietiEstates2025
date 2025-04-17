import { BBox } from '../scene/bbox';
import { Path } from '../scene/shape/path';
import type { FocusSwapChain } from './focusSwapChain';
export declare class FocusIndicator {
    private readonly swapChain;
    private readonly element;
    private readonly svg;
    private readonly outerPath;
    private readonly innerPath;
    private readonly div;
    private hasBeenActivated;
    constructor(swapChain: FocusSwapChain);
    clear(): void;
    update(focus: BBox | Path, rect: BBox | undefined, clip: boolean): void;
    private onSwap;
    private show;
    private focusVisible?;
    overrideFocusVisible(focusVisible: boolean | undefined): void;
    isFocusVisible(force?: boolean): boolean;
}
