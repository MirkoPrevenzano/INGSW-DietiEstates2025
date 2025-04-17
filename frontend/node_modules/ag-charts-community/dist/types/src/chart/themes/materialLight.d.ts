import { ChartTheme } from './chartTheme';
export declare class MaterialLight extends ChartTheme {
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
            YELLOW: string;
            VIOLET: string;
            GRAY: string;
            MAGENTA: string;
            BROWN: string;
            RED: string;
        };
        sequentialColors: Record<string | number, string[]>;
        divergingColors: string[];
        secondSequentialColors: string[];
        secondDivergingColors: string[];
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
        hierarchyColors: string[];
        secondHierarchyColors: string[];
    };
    getTemplateParameters(): Map<any, any>;
}
