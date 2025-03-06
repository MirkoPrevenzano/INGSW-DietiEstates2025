import type { RovingDirection } from './rovingDirection';
import { Widget } from './widget';
type SliderStep = typeof SliderWidget.STEP_ONE | typeof SliderWidget.STEP_HUNDRETH;
export declare class SliderWidget extends Widget<HTMLInputElement> {
    static readonly STEP_ONE: {
        readonly attributeValue: "1";
        readonly divider: 1;
    };
    static readonly STEP_HUNDRETH: {
        readonly attributeValue: "0.01";
        readonly divider: 100;
    };
    private _step;
    private _keyboardStep?;
    get step(): Readonly<SliderStep>;
    set step(step: Readonly<SliderStep>);
    get keyboardStep(): Readonly<SliderStep> | undefined;
    set keyboardStep(step: Readonly<SliderStep> | undefined);
    get orientation(): RovingDirection;
    set orientation(orientation: RovingDirection);
    constructor();
    protected destructor(): void;
    clampValueRatio(clampMin: number, clampMax: number): number;
    setValueRatio(ratio: number, opts?: {
        ariaValueText?: string;
    }): void;
    getValueRatio(): number;
    private static registerDefaultPreventers;
    private static onKeyDown;
}
export {};
