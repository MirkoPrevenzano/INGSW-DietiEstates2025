import type { Selection } from '../../../scene/selection';
import type { Path } from '../../../scene/shape/path';
import type { AnimationManager } from '../../interaction/animationManager';
import type { NodeDataDependant } from '../seriesTypes';
export declare function pathSwipeInAnimation({ id, visible, nodeDataDependencies }: {
    id: string;
    visible: boolean;
} & NodeDataDependant, animationManager: AnimationManager, ...paths: Path[]): void;
export declare function pathFadeInAnimation<T>({ id }: {
    id: string;
}, subId: string, animationManager: AnimationManager, phase?: 'add' | 'trailing', ...selection: Selection<Path, T>[] | Path[]): void;
export declare function buildResetPathFn(opts: {
    getVisible(): boolean;
    getOpacity(): number;
}): (_node: Path) => {
    visible: boolean;
    opacity: number;
    clipScalingX: number;
    clip: boolean;
};
export declare function updateClipPath({ nodeDataDependencies }: NodeDataDependant, path: Path): void;
