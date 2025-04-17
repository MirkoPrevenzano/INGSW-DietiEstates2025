export declare const DEFAULT_FILLS: {
    BLUE: string;
    ORANGE: string;
    GREEN: string;
    CYAN: string;
    YELLOW: string;
    VIOLET: string;
    GRAY: string;
    MAGENTA: string;
    BROWN: string;
    RED: string;
};
export declare const DEFAULT_STROKES: {
    BLUE: string;
    ORANGE: string;
    GREEN: string;
    CYAN: string;
    YELLOW: string;
    VIOLET: string;
    GRAY: string;
    MAGENTA: string;
    BROWN: string;
    RED: string;
};
export type DefaultColors = {
    fills: {
        [key: string]: string;
    };
    fillsFallback: string[];
    strokes: {
        [key: string]: string;
    };
    sequentialColors: {
        [key: string]: string[];
    };
    divergingColors: string[];
    hierarchyColors: string[];
    secondSequentialColors: string[];
    secondDivergingColors: string[];
    secondHierarchyColors: string[];
    up: {
        stroke: string;
        fill: string;
    };
    down: {
        stroke: string;
        fill: string;
    };
    neutral: {
        stroke: string;
        fill: string;
    };
    altUp: {
        stroke: string;
        fill: string;
    };
    altDown: {
        stroke: string;
        fill: string;
    };
    altNeutral: {
        stroke: string;
        fill: string;
    };
};
