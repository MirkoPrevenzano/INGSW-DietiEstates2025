import type { AgTooltipAnchorTo, AgTooltipMode, AgTooltipPlacement, InteractionRange, TextWrap } from 'ag-charts-types';
import type { DOMManager } from '../../dom/domManager';
import type { LocaleManager } from '../../locale/localeManager';
import { BaseProperties } from '../../util/properties';
import { type TooltipContent, type TooltipPaginationState } from './tooltipContent';
export { DEFAULT_TOOLTIP_CLASS, DEFAULT_TOOLTIP_DARK_CLASS, tooltipHtml, tooltipContentAriaLabel, type TooltipContent, type TooltipPaginationState, type TooltipContentDataRow, type TooltipStructuredContent, } from './tooltipContent';
type TooltipPositionType = 'pointer' | 'node' | 'top' | 'right' | 'bottom' | 'left' | 'top-left' | 'top-right' | 'bottom-right' | 'bottom-left';
type TooltipOffsets = {
    canvasX: number;
    canvasY: number;
    nodeCanvasX?: number;
    nodeCanvasY?: number;
};
export type TooltipEventType = 'pointermove' | 'click' | 'dblclick' | 'keyboard';
export type TooltipPointerEvent<T extends TooltipEventType = TooltipEventType> = Readonly<TooltipOffsets> & {
    readonly type: T;
};
export interface TooltipMetaPosition {
    anchorTo?: AgTooltipAnchorTo;
    defaultAnchorTo?: AgTooltipAnchorTo;
    placement?: AgTooltipPlacement | AgTooltipPlacement[];
    defaultPlacement?: AgTooltipPlacement | AgTooltipPlacement[];
    xOffset?: number;
    yOffset?: number;
}
export interface TooltipMeta extends TooltipOffsets {
    showArrow?: boolean;
    position?: TooltipMetaPosition;
    enableInteraction?: boolean;
}
export declare class TooltipPosition extends BaseProperties {
    /**
     * @todo(AG-10870) - this should never be undefined, but there's something odd going on with
     * theming that doesn't look so easy to fix. This property will be removed in the next major,
     * so for now we'll just work around. It's marked as protected no code outside of this class
     * can use it, and all code will use the newer `placement` and `anchorTo` properties, which
     * derive their defaults from this property. Eventually those properties will be set via the
     * theme, and we'll make sure they are applied normally.
     */
    /** The type of positioning for the tooltip. By default, the tooltip follows the pointer. */
    protected type?: TooltipPositionType;
    /** @todo Remove this when type is removed. */
    protected _seriesOverrideType?: TooltipPositionType;
    /** The horizontal offset in pixels for the position of the tooltip. */
    xOffset: number;
    /** The vertical offset in pixels for the position of the tooltip. */
    yOffset: number;
    anchorTo?: AgTooltipAnchorTo;
    placement?: AgTooltipPlacement | AgTooltipPlacement[];
    get defaultAnchorTo(): AgTooltipAnchorTo;
    get defaultPlacement(): AgTooltipPlacement;
}
export declare class Tooltip extends BaseProperties {
    enabled: boolean;
    mode: AgTooltipMode;
    showArrow?: boolean;
    delay: number;
    range?: InteractionRange;
    wrapping: TextWrap;
    readonly position: TooltipPosition;
    readonly pagination = false;
    darkTheme: boolean;
    /** Escape-hatch for changes in AG-11645. */
    bounds: 'extended' | 'canvas';
    private readonly destroyFns;
    private readonly springAnimation;
    private enableInteraction;
    private readonly wrapTypes;
    private element?;
    private readonly sizeMonitor;
    private _elementSize;
    private _showTimeout;
    private arrowPosition;
    private _visible;
    private positionParams;
    get interactive(): boolean;
    constructor();
    private localeManager;
    setup(localeManager: LocaleManager, domManager: DOMManager): () => void;
    isVisible(): boolean;
    contains(node: Node | null): boolean;
    private updateTooltipPosition;
    /**
     * Shows tooltip at the given event's coordinates.
     * If the `html` parameter is missing, moves the existing tooltip to the new position.
     */
    show(boundingRect: DOMRect, canvasRect: DOMRect, meta: TooltipMeta, content: TooltipContent[] | null, pagination?: TooltipPaginationState, instantly?: boolean): void;
    hide(): void;
    private toggle;
    private toggleCallback;
    private updateClassModifiers;
    private getTooltipBounds;
}
