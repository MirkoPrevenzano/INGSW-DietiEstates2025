import type { AnimationManager } from '../chart/interaction/animationManager';
import type { Node } from '../scene/node';
import type { Selection } from '../scene/selection';
import type { Interpolating } from '../util/interpolating';
import type { AnimationPhase, AnimationValue } from './animation';
export type NodeUpdateState = 'unknown' | 'added' | 'removed' | 'updated' | 'no-op';
export type FromToMotionPropFnContext<T> = {
    last: boolean;
    lastLive: boolean;
    prev?: T;
    prevFromProps?: Partial<T>;
    next?: T;
    prevLive?: T;
    nextLive?: T;
};
export type ExtraOpts<T> = {
    phase: AnimationPhase;
    delay?: number;
    duration?: number;
    start?: Partial<T>;
    finish?: Partial<T>;
};
export type FromToMotionPropFn<N extends Node, T extends Record<string, string | number | Interpolating | undefined> & Partial<N>, D> = (node: N, datum: D, state: NodeUpdateState, ctx: FromToMotionPropFnContext<N>) => T & Partial<ExtraOpts<N>>;
export type ApplyFn<N extends Node, T extends Record<string, string | number | Interpolating | undefined> & Partial<N>> = (note: N, props: T, status: 'start' | 'update' | 'end') => void;
export declare const NODE_UPDATE_STATE_TO_PHASE_MAPPING: Record<NodeUpdateState, AnimationPhase>;
export interface FromToDiff {
    changed: boolean;
    added: Set<string> | Map<string, any>;
    removed: Set<string> | Map<string, any>;
}
export interface FromToFns<N extends Node, T extends Record<string, string | number | Interpolating | undefined> & Partial<N>, D> {
    fromFn: FromToMotionPropFn<N, T, D>;
    toFn: FromToMotionPropFn<N, T, D>;
    applyFn?: ApplyFn<N, T>;
}
/**
 * Implements a per-node "from/to" animation, with support for detecting added, moved, and removed
 * nodes. Animates nodes based on their properties and state transitions, utilizing the provided
 * callbacks to determine the properties at different stages of the animation.
 *
 * @template N Node type, typically extending the base node class.
 * @template T Type of properties to be animated. Must be a record of properties with string, number,
 *             or interpolating values and must be partially assignable to N.
 * @template D Data type associated with each node.
 *
 * @param groupId A unique identifier for the group of animations.
 * @param subId A sub-identifier for animations related to this specific invocation.
 * @param animationManager Manager responsible for handling the animations.
 * @param selectionsOrNodes Array of either selections (containing nodes) or nodes themselves, to be animated.
 * @param fns Object containing the necessary callbacks for determining 'from' and 'to' properties:
 *            - fromFn: Callback to determine starting properties per node.
 *            - toFn: Callback to determine final properties per node.
 *            - intermediateFn: (optional) Callback for intermediate steps of animation.
 *            - applyFn: (optional) Function to apply the properties to the node. Defaults to setting
 *                       node properties directly.
 * @param getDatumId (Optional) Function for generating a unique identifier for each datum, used in
 *                   conjunction with the diff parameter to detect added/moved/removed cases.
 * @param diff (Optional) Diff data model used for detecting changes in the node state (added, moved, removed).
 */
export declare function fromToMotion<N extends Node, T extends Record<string, string | number | Interpolating | undefined> & Partial<N>, D>(groupId: string, subId: string, animationManager: AnimationManager, selectionsOrNodes: Selection<N, D>[] | N[], fns: FromToFns<N, T, D>, getDatumId?: (node: N, datum: D) => string, diff?: FromToDiff): void;
/**
 * Implements a batch "from/to" animation, where all nodes transition uniformly between a starting
 * and final set of properties. This is useful for static animations where the same properties
 * apply to all nodes in the batch.
 *
 * @template N Node type, typically extending the base node class.
 * @template T Type of properties to be animated. Must extend `AnimationValue` and be partially assignable to `N`.
 * @template D Data type associated with each node.
 *
 * @param groupId A unique identifier for the group of animations.
 * @param subId A sub-identifier for animations related to this specific invocation.
 * @param animationManager Manager responsible for handling the animations.
 * @param selectionsOrNodes Array of either selections (containing nodes) or nodes themselves, to be animated.
 * @param from Initial properties for the nodes at the start of the animation.
 * @param to Final properties for the nodes at the end of the animation.
 * @param extraOpts Optional additional animation properties, such as:
 *                  - start: Properties to apply when the animation starts.
 *                  - finish: Properties to apply when the animation ends.
 *                  - phase: Animation phase (e.g., 'update', 'enter', 'exit').
 */
export declare function staticFromToMotion<N extends Node, T extends AnimationValue & Partial<N> & object, D>(groupId: string, subId: string, animationManager: AnimationManager, selectionsOrNodes: Selection<N, D>[] | N[], from: T, to: T, extraOpts: ExtraOpts<N>): void;
