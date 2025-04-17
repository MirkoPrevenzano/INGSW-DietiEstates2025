import type { InternalAgColorType } from 'ag-charts-core';
import type { FromToMotionPropFn } from '../../../motion/fromToMotion';
import type { Point } from '../../../scene/point';
import type { Sector } from '../../../scene/shape/sector';
import type { Marker } from '../../marker/marker';
import type { SeriesNodePickMatch } from '../series';
type AnimatableSectorDatum = {
    radius: number;
    innerRadius: number;
    outerRadius: number;
    startAngle: number;
    endAngle: number;
    sectorFormat: {
        fill?: InternalAgColorType;
        stroke?: string;
    };
};
type ScaleFn = {
    convert(x: number): number;
};
export declare function preparePieSeriesAnimationFunctions(initialLoad: boolean, rotationDegrees: number, scaleFn: ScaleFn, oldScaleFn: ScaleFn): {
    nodes: {
        toFn: FromToMotionPropFn<Sector<any>, any, AnimatableSectorDatum>;
        fromFn: FromToMotionPropFn<Sector<any>, any, AnimatableSectorDatum>;
    };
    innerCircle: {
        fromFn: FromToMotionPropFn<Marker, any, {
            radius: number;
        }>;
        toFn: FromToMotionPropFn<Marker, any, {
            radius: number;
        }>;
    };
};
export declare function resetPieSelectionsFn(_node: Sector, datum: AnimatableSectorDatum): {
    startAngle: number;
    endAngle: number;
    innerRadius: number;
    outerRadius: number;
    stroke: string | undefined;
};
type SectorVariables = {
    readonly innerRadius: number;
    readonly outerRadius: number;
    readonly startAngle: number;
    readonly endAngle: number;
};
type SectorSceneNode = SectorVariables & {
    readonly datum: any;
};
type SectorSeries = {
    centerX: number;
    centerY: number;
    getItemNodes(): SectorSceneNode[];
};
export declare function pickByMatchingAngle(series: SectorSeries, point: Point): SeriesNodePickMatch | undefined;
export {};
