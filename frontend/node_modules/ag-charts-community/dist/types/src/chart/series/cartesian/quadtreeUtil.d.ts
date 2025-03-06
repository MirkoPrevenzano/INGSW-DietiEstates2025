import type { Group } from '../../../scene/group';
import type { Node } from '../../../scene/node';
import type { Point } from '../../../scene/point';
import type { QuadtreeNearest } from '../../../scene/util/quadtree';
import type { DistantObject } from '../../../util/nearest';
import type { SeriesNodePickMatch } from '../series';
import type { SeriesNodeDatum } from '../seriesTypes';
export type QuadtreeCompatibleNode = Node & DistantObject & {
    readonly midPoint: {
        x: number;
        y: number;
    };
};
export declare function addHitTestersToQuadtree<TNode extends QuadtreeCompatibleNode, TDatum extends SeriesNodeDatum<unknown>>(quadtree: QuadtreeNearest<TDatum>, hitTesters: Iterable<TNode>): void;
type SeriesWithQuadtreeNearest<TDatum extends SeriesNodeDatum<unknown>> = {
    readonly contentGroup: Group;
    getQuadTree(): QuadtreeNearest<TDatum>;
};
export declare function findQuadtreeMatch<TDatum extends SeriesNodeDatum<unknown>>(series: SeriesWithQuadtreeNearest<TDatum>, point: Point): SeriesNodePickMatch | undefined;
export {};
