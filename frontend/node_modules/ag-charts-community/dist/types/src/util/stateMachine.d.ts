type StateDefinition<State extends string, Events extends Record<string, any>> = {
    [key in keyof Events]?: Destination<State, Events[key]>;
};
type Destination<State extends string, Data> = Array<StateTransition<State, Data>> | StateTransition<State, Data> | StateTransitionAction<Data> | State | HierarchyState | StateMachine<any, any>;
type StateUtils<State extends string> = {
    onEnter?: (from?: State, data?: any) => void;
    onExit?: () => void;
};
type StateTransition<State, Data> = {
    /**
     * Return `false` to prevent the action and transition. The FSM will pick the first transition that passes its
     * guard or does not have one.
     */
    guard?: (data: Data) => boolean;
    target?: State | HierarchyState | StateMachine<any, any>;
    action?: StateTransitionAction<Data>;
};
type StateTransitionAction<Data> = (data: Data) => void;
type HierarchyState = '__parent' | '__child';
export declare function StateMachineProperty(): PropertyDecorator;
declare abstract class AbstractStateMachine<Events extends Record<string, any>> {
    parent?: AbstractStateMachine<Events>;
    abstract transition<Event extends keyof Events & string>(event: Event, data?: Events[Event]): void;
    abstract transitionAsync<Event extends keyof Events & string>(event: Event, data?: Events[Event]): void;
    transitionRoot<Event extends keyof Events & string>(event: Event, data?: Events[Event]): void;
}
/**
 * A Hierarchical Finite State Machine is a system that must be in exactly one of a list of states, where those states
 * can also be other state machines. It can only transition between one state and another if a transition event is
 * provided between the two states.
 */
export declare class StateMachine<State extends string, Events extends Record<string, any>> extends AbstractStateMachine<Events> {
    private readonly defaultState;
    private readonly states;
    private readonly enterEach?;
    static readonly child: "__child";
    static readonly parent: "__parent";
    protected readonly debug: import("./debug").DebugLogger;
    private state;
    private childState?;
    constructor(defaultState: State, states: Record<State, StateDefinition<State, Events> & StateUtils<State>>, enterEach?: ((from: State | HierarchyState, to: State | HierarchyState) => void) | undefined);
    transition<Event extends keyof Events & string>(event: Event, data?: Events[Event]): void;
    transitionAsync<Event extends keyof Events & string>(event: Event, data?: Events[Event]): void;
    protected is(value: unknown): boolean;
    protected resetHierarchy(): void;
    private transitionChild;
    private getDestinationState;
}
export declare class ParallelStateMachine<State extends string, Events extends Record<string, any>> extends AbstractStateMachine<Events> {
    private readonly stateMachines;
    constructor(...stateMachines: Array<StateMachine<State, Events>>);
    transition<Event extends keyof Events & string>(event: Event, data?: Events[Event]): void;
    transitionAsync<Event extends keyof Events & string>(event: Event, data?: Events[Event]): void;
}
export {};
