import type { DOMManager } from '../../dom/domManager';
import { type Widget } from '../../widget/widget';
import { DragInterpreter } from './dragInterpreter';
export declare class WidgetSet {
    readonly seriesWidget: Widget;
    readonly chartWidget: Widget;
    readonly containerWidget: Widget;
    readonly seriesDragInterpreter: DragInterpreter;
    constructor(domManager: DOMManager);
    destroy(): void;
}
