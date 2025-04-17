export type DebugLoggerMethods = {
    check(): boolean;
    group<T>(name: string, cb: () => T): T;
};
export type DebugLogger = ((...logContent: any[]) => void) & DebugLoggerMethods;
export declare const Debug: {
    create(...debugSelectors: Array<boolean | string>): DebugLogger;
    check(...debugSelectors: Array<boolean | string>): boolean;
    inDevelopmentMode<R>(fn: () => R): R | undefined;
};
interface DebugTimingOpts {
    logResult: boolean;
    logStack: boolean;
    logArgs: boolean;
    logData: (target: any) => any;
}
export declare function DebugTiming(name: string, opts: Partial<DebugTimingOpts>): (_target: any, _propertyKey: string, descriptor: PropertyDescriptor) => void;
export {};
