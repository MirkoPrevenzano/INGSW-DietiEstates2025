import { Path } from './path';
export declare class SvgPath<D = any> extends Path<D> {
    x: number;
    y: number;
    private readonly commands;
    private _d;
    get d(): string;
    set d(d: string);
    constructor(d?: string);
    updatePath(): void;
}
