import type { DOMManager } from '../../dom/domManager';
import type { LocaleManager } from '../../locale/localeManager';
import type { BBoxValues } from '../../util/bboxinterface';
import type { ErrorBoundSeriesNodeDatum, ISeries, SeriesNodeDatum } from '../series/seriesTypes';
import type { Tooltip, TooltipContent, TooltipMeta, TooltipPaginationState, TooltipPointerEvent } from '../tooltip/tooltip';
/**
 * Manages the tooltip HTML an element. Tracks the requested HTML from distinct dependents and
 * handles conflicting tooltip requests.
 */
export declare class TooltipManager {
    private readonly domManager;
    private readonly tooltip;
    private readonly stateTracker;
    private readonly suppressState;
    private appliedState;
    private readonly destroyFns;
    constructor(localeManager: LocaleManager, domManager: DOMManager, tooltip: Tooltip);
    destroy(): void;
    updateTooltip(callerId: string, meta?: TooltipMeta, content?: TooltipContent[], pagination?: TooltipPaginationState): void;
    removeTooltip(callerId: string): void;
    suppressTooltip(callerId: string): void;
    unsuppressTooltip(callerId: string): void;
    private applyStates;
    static makeTooltipMeta(event: TooltipPointerEvent, series: ISeries<any, any, any>, datum: SeriesNodeDatum<unknown> & Pick<ErrorBoundSeriesNodeDatum, 'yBar'>, movedBounds: BBoxValues | undefined): TooltipMeta;
    isEnteringInteractiveTooltip(event: Pick<FocusEvent | MouseEvent, 'relatedTarget'>): boolean;
}
