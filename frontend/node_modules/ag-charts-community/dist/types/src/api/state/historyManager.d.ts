import type { ChartEventManager } from '../../chart/interaction/chartEventManager';
import type { MementoOriginator } from './memento';
export declare class HistoryManager {
    private history;
    private historyIndex;
    private readonly originators;
    private readonly clearState;
    private readonly maxHistoryLength;
    private readonly debug;
    private readonly destroyFns;
    constructor(chartEventManager: ChartEventManager);
    destroy(): void;
    addMementoOriginator(originator: MementoOriginator): void;
    clear(): void;
    record(label: string, ...originators: Array<MementoOriginator>): void;
    undo(): void;
    redo(): void;
    private findPreviousMemento;
    private restoreMemento;
    private debugEvent;
}
