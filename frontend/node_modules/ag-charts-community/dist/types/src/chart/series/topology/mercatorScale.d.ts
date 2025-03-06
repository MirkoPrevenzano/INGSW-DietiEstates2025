import { AbstractScale } from '../../../scale/abstractScale';
import type { NormalizedDomain } from '../../../scale/scale';
import { BBox } from '../../../scene/bbox';
import type { Position } from './geojson';
type XY = [x: number, y: number];
export declare class MercatorScale extends AbstractScale<Position, XY> {
    readonly domain: Position[];
    readonly range: XY[];
    readonly type = "mercator";
    readonly bounds: BBox;
    static bounds(domain: Position[]): BBox;
    static fixedScale(): MercatorScale;
    constructor(domain: Position[], range: XY[]);
    toDomain(): Position | undefined;
    normalizeDomains(...domains: Position[][]): NormalizedDomain<Position>;
    convert([lon, lat]: Position): XY;
    invert([x, y]: XY): Position;
}
export {};
