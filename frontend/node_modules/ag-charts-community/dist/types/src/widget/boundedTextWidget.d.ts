import { Widget } from './widget';
export declare class BoundedTextWidget extends Widget<HTMLDivElement> {
    private readonly svgElement;
    private readonly textElement;
    set textContent(text: string | null);
    get textContent(): string | null;
    constructor();
    protected destructor(): void;
}
