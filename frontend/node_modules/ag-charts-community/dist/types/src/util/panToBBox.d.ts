import type { BBoxValues } from './bboxinterface';
type Ratios = {
    min: number;
    max: number;
};
type XYRatios = {
    x: Ratios;
    y: Ratios;
};
export declare function calcPanToBBoxRatios(viewportBBox: BBoxValues, ratios: Partial<XYRatios>, targetBBox: BBoxValues): XYRatios;
export {};
