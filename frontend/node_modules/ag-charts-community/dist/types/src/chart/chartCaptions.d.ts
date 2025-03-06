import type { LayoutContext } from '../module/baseModule';
import { Caption } from './caption';
import { type LayoutCompleteEvent } from './layout/layoutManager';
export declare class ChartCaptions {
    readonly title: Caption;
    readonly subtitle: Caption;
    readonly footnote: Caption;
    positionCaptions(ctx: LayoutContext): void;
    positionAbsoluteCaptions(ctx: LayoutCompleteEvent): void;
    private computeX;
    private positionCaption;
    private shrinkLayoutByCaption;
}
