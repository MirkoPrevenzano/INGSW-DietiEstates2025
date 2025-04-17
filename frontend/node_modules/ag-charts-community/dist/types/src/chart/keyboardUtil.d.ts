import { BBox } from '../scene/bbox';
import type { Path } from '../scene/shape/path';
import type { ISeries } from './series/seriesTypes';
import { getDatumRefPoint } from './series/util';
import type { TooltipPointerEvent } from './tooltip/tooltip';
type PickProperties = {
    bounds: Path | BBox | undefined;
    datum: Parameters<typeof getDatumRefPoint>[1];
    movedBounds?: Parameters<typeof getDatumRefPoint>[2];
    clipFocusBox: boolean;
};
export declare function getPickedFocusBBox({ bounds }: PickProperties): Readonly<BBox>;
export declare function makeKeyboardPointerEvent(series: ISeries<any, any, any>, hoverRect: BBox, pick: PickProperties): TooltipPointerEvent<'keyboard'> | undefined;
export {};
