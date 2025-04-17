import { ChartTheme } from './chartTheme';
export declare class PolychromaLight extends ChartTheme {
    getDefaultColors(): {
        fills: {
            BLUE: string;
            PURPLE: string;
            MAGENTA: string;
            PINK: string;
            RED: string;
            ORANGE: string;
            YELLOW: string;
            GREEN: string;
            CYAN: string;
            MODERATE_BLUE: string;
            GRAY: string;
        };
        fillsFallback: string[];
        strokes: {
            BLUE: string;
            PURPLE: string;
            MAGENTA: string;
            PINK: string;
            RED: string;
            ORANGE: string;
            YELLOW: string;
            GREEN: string;
            CYAN: string;
            MODERATE_BLUE: string;
            GRAY: string;
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
