import type { AxisContext } from '../../module/axisContext';
import { Group } from '../../scene/group';
import { Node } from '../../scene/node';
import type { ChartAxisDirection } from '../chartAxisDirection';
interface AxisNodes {
    axisNode: Node;
    gridNode: Node;
    crossLineRangeNode: Node;
    crossLineLineNode: Node;
    crossLineLabelNode: Node;
    labelNode: Node;
}
interface Axis {
    createAxisContext(): AxisContext;
    attachAxis(nodes: AxisNodes): void;
    detachAxis(nodes: AxisNodes): void;
    destroy(): void;
}
export declare class AxisManager {
    private readonly sceneRoot;
    private readonly axes;
    readonly axisGridGroup: Group<any>;
    readonly axisGroup: Group<any>;
    readonly axisLabelGroup: Group<any>;
    readonly axisCrosslineRangeGroup: Group<any>;
    readonly axisCrosslineLineGroup: Group<any>;
    readonly axisCrosslineLabelGroup: Group<any>;
    constructor(sceneRoot: Group);
    updateAxes(oldAxes: Axis[], newAxes: Axis[]): void;
    getAxisContext(direction: ChartAxisDirection): AxisContext[];
    destroy(): void;
}
export {};
