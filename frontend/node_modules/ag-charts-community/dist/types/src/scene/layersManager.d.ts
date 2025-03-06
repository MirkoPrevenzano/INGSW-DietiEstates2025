import { HdpiCanvas } from './canvas/hdpiCanvas';
import { HdpiOffscreenCanvas } from './canvas/hdpiOffscreenCanvas';
export declare class LayersManager {
    readonly canvas: HdpiCanvas;
    readonly debug: import("../util/debug").DebugLogger;
    private readonly layersMap;
    private nextLayerId;
    constructor(canvas: HdpiCanvas);
    get size(): number;
    resize(width: number, height: number, pixelRatio: number): void;
    addLayer(opts: {
        name?: string;
    }): HdpiOffscreenCanvas;
    removeLayer(canvas: HdpiOffscreenCanvas): void;
    clear(): void;
}
