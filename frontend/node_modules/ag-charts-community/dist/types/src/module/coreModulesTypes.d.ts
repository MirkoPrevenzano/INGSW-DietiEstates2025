import type { AgChartThemePalette } from 'ag-charts-types';
import type { SeriesOptionsTypes } from '../chart/mapping/types';
export type RequiredSeriesType = NonNullable<SeriesOptionsTypes['type']>;
export type PaletteType = 'inbuilt' | 'user-indexed' | 'user-full';
export declare function paletteType(partial?: AgChartThemePalette): PaletteType;
