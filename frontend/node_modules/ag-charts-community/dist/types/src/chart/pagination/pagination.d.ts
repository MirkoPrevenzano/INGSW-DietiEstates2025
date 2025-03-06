import type { AgChartLegendOrientation, AgMarkerShape, FontStyle, FontWeight } from 'ag-charts-types';
import type { Node } from '../../scene/node';
import { BaseProperties } from '../../util/properties';
import { ChartUpdateType } from '../chartUpdateType';
declare class PaginationLabel extends BaseProperties {
    color: string;
    fontStyle?: FontStyle;
    fontWeight?: FontWeight;
    fontSize: number;
    fontFamily: string;
}
declare class PaginationMarkerStyle extends BaseProperties {
    size: number;
    fill?: string;
    fillOpacity?: number;
    stroke?: string;
    strokeWidth: number;
    strokeOpacity: number;
}
declare class PaginationMarker extends BaseProperties {
    readonly parent: Pagination;
    shape: AgMarkerShape;
    size: number;
    /**
     * Inner padding between a pagination button and the label.
     */
    padding: number;
    constructor(parent: Pagination);
}
export declare class Pagination extends BaseProperties {
    private readonly chartUpdateCallback;
    private readonly pageUpdateCallback;
    static readonly className = "Pagination";
    readonly id: string;
    readonly marker: PaginationMarker;
    readonly activeStyle: PaginationMarkerStyle;
    readonly inactiveStyle: PaginationMarkerStyle;
    readonly highlightStyle: PaginationMarkerStyle;
    readonly label: PaginationLabel;
    private readonly group;
    private readonly labelNode;
    private highlightActive?;
    constructor(chartUpdateCallback: (type: ChartUpdateType) => void, pageUpdateCallback: (newPage: number) => void);
    totalPages: number;
    currentPage: number;
    translationX: number;
    translationY: number;
    private nextButtonDisabled;
    private previousButtonDisabled;
    private _visible;
    set visible(value: boolean);
    get visible(): boolean;
    private _enabled;
    set enabled(value: boolean);
    get enabled(): boolean;
    private updateGroupVisibility;
    private _orientation;
    set orientation(value: AgChartLegendOrientation);
    get orientation(): AgChartLegendOrientation;
    private readonly nextButton;
    private readonly previousButton;
    update(): void;
    private updatePositions;
    private updateLabelPosition;
    private updateNextButtonPosition;
    private updateLabel;
    updateMarkers(): void;
    private updateMarker;
    private enableOrDisableButtons;
    setPage(pageNumber: number): void;
    getCursor(node: 'previous' | 'next'): "pointer" | undefined;
    onClick(event: MouseEvent, node: 'previous' | 'next'): void;
    onMouseHover(node: 'previous' | 'next' | undefined): void;
    private onPaginationChanged;
    private incrementPage;
    private decrementPage;
    onMarkerShapeChange(): void;
    attachPagination(node: Node): void;
    getBBox(): import("../../integrated-charts-scene").BBox;
    computeCSSBounds(): {
        prev: import("../../integrated-charts-scene").BBox;
        next: import("../../integrated-charts-scene").BBox;
    };
}
export {};
