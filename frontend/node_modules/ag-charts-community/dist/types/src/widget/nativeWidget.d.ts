import { Widget } from './widget';
export declare class NativeWidget<TElem extends HTMLElement = HTMLElement> extends Widget<TElem> {
    constructor(elem: TElem);
    protected destructor(): void;
}
