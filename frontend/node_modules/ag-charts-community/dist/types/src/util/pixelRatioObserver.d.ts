/**
 * Chrome & FireFox reports devicePixelRatio as the pixel ratio of the screen multiplied by the browser zoom.
 * Safari reports this as just the screen pixel ratio.
 * There's not a reliable way get the browser zoom - outerWidth / innerWidth doesn't work in iframes, and no API gives this value.
 * Therefore, this works as intended in Chrome & FireFox, and doesn't make things worse in Safari.
 */
export declare class PixelRatioObserver {
    private readonly callback;
    get pixelRatio(): number;
    private devicePixelRatio;
    private devicePixelRatioMediaQuery;
    private readonly devicePixelRatioListener;
    constructor(callback: (pixelRatio: number) => void);
    observe(): void;
    disconnect(): void;
    private unregisterDevicePixelRatioListener;
    private registerDevicePixelRatioListener;
}
