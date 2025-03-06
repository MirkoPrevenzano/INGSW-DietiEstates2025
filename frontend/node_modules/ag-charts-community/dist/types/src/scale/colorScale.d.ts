import { AbstractScale } from './abstractScale';
import type { NormalizedDomain } from './scale';
export declare class ColorScale extends AbstractScale<number, string> {
    readonly type = "color";
    protected invalid: boolean;
    domain: number[];
    range: string[];
    private parsedRange;
    update(): void;
    normalizeDomains(...domains: number[][]): NormalizedDomain<number>;
    toDomain(): number | undefined;
    convert(x: number): string;
    invert(): number | undefined;
    protected refresh(): void;
}
