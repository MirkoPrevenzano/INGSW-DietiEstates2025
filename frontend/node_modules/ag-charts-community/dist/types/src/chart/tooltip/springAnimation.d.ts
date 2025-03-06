import { Listeners } from '../../util/listeners';
interface SpringAnimationUpdateEvent {
    readonly type: 'update';
    readonly x: number;
    readonly y: number;
}
type UpdateEventHandler = (e: SpringAnimationUpdateEvent) => void;
export declare class SpringAnimation extends Listeners<'update', UpdateEventHandler> {
    private x1;
    private y1;
    x: number;
    y: number;
    private vx;
    private vy;
    private t0;
    private animationFrameHandle;
    reset(): void;
    update(x: number, y: number): void;
    private onFrame;
    private emitUpdate;
}
export {};
