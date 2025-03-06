import { BBox } from './bbox';
import type { LayersManager } from './layersManager';
import { type Node, type RenderContext } from './node';
export declare enum DebugSelectors {
    SCENE = "scene",
    SCENE_STATS = "scene:stats",
    SCENE_STATS_VERBOSE = "scene:stats:verbose",
    SCENE_DIRTY_TREE = "scene:dirtyTree"
}
type BuildTree = {
    name?: string;
    node?: any;
    dirty?: boolean;
};
export declare function debugStats(layersManager: LayersManager, debugSplitTimes: Record<string, number>, ctx: CanvasRenderingContext2D, renderCtxStats: RenderContext['stats'], extraDebugStats?: {}, seriesRect?: BBox): void;
export declare function prepareSceneNodeHighlight(ctx: RenderContext): void;
export declare function debugSceneNodeHighlight(ctx: CanvasRenderingContext2D, debugNodes: Record<string, Node>): void;
export declare const skippedProperties: Set<string>;
export declare function buildTree(node: Node, mode: 'json' | 'console'): BuildTree;
export declare function buildDirtyTree(node: Node): {
    dirtyTree: {
        name?: string;
        node?: any;
        dirty?: boolean;
    };
    paths: string[];
};
export {};
