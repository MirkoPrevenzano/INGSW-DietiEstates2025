import { ChartTheme } from './chartTheme';
export declare class VividLight extends ChartTheme {
    getDefaultColors(): {
        fills: {
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
        fillsFallback: string[];
        strokes: {
            BLUE: string;
            ORANGE: string;
            GREEN: string;
            CYAN: string;
            VIOLET: string;
            YELLOW: string;
            GRAY: string;
            MAGENTA: string;
            BROWN: string;
            RED: string;
        };
        sequentialColors: Record<string | number, string[]>;
        divergingColors: string[];
        hierarchyColors: never[];
        secondSequentialColors: string[];
        secondDivergingColors: string[];
        secondHierarchyColors: never[];
        up: {
            fill: string;
            stroke: string;
        };
        down: {
            fill: string;
            stroke: string;
        };
        neutral: {
            fill: string;
            stroke: string;
        };
        altUp: {
            fill: string;
            stroke: string;
        };
        altDown: {
            fill: string;
            stroke: string;
        };
        altNeutral: {
            fill: string;
            stroke: string;
        };
    };
    getTemplateParameters(): Map<any, any>;
}
