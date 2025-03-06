import { AfterViewChecked, AfterViewInit, ElementRef, OnChanges, OnDestroy, OnInit, Renderer2, SimpleChanges } from '@angular/core';
import * as i0 from "@angular/core";
/**
 * Slick component
 */
export declare class SlickCarouselComponent implements OnDestroy, OnChanges, AfterViewInit, AfterViewChecked {
    config: JQuerySlickOptions;
    afterChange: import("@angular/core").OutputEmitterRef<{
        event: JQuery.TriggeredEvent<HTMLElement>;
        slick: JQuerySlick;
        currentSlide: number;
        first: boolean;
        last: boolean;
    }>;
    beforeChange: import("@angular/core").OutputEmitterRef<{
        event: JQuery.TriggeredEvent<HTMLElement>;
        slick: JQuerySlick;
        currentSlide: number;
        nextSlide: number;
    }>;
    breakpoint: import("@angular/core").OutputEmitterRef<{
        event: JQuery.TriggeredEvent<HTMLElement>;
        slick: JQuerySlick;
        breakpoint: any;
    }>;
    destroy: import("@angular/core").OutputEmitterRef<{
        event: JQuery.TriggeredEvent<HTMLElement>;
        slick: JQuerySlick;
    }>;
    init: import("@angular/core").OutputEmitterRef<{
        event: JQuery.TriggeredEvent<HTMLElement>;
        slick: JQuerySlick;
    }>;
    $instance: JQuery;
    private currentIndex;
    slides: any[];
    initialized: boolean;
    private _removedSlides;
    private _addedSlides;
    private el;
    private zone;
    private isServer;
    /**
     * On component destroy
     */
    ngOnDestroy(): void;
    ngAfterViewInit(): void;
    /**
     * On component view checked
     */
    ngAfterViewChecked(): void;
    /**
     * init slick
     */
    initSlick(): void;
    addSlide(slickItem: SlickItemDirective): void;
    removeSlide(slickItem: SlickItemDirective): void;
    /**
     * Slick Method
     */
    slickGoTo(index: number): void;
    slickNext(): void;
    slickPrev(): void;
    slickPause(): void;
    slickPlay(): void;
    unslick(): void;
    ngOnChanges(changes: SimpleChanges): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<SlickCarouselComponent, never>;
    static ɵcmp: i0.ɵɵComponentDeclaration<SlickCarouselComponent, "ngx-slick-carousel", ["slick-carousel"], { "config": { "alias": "config"; "required": false; }; }, { "afterChange": "afterChange"; "beforeChange": "beforeChange"; "breakpoint": "breakpoint"; "destroy": "destroy"; "init": "init"; }, never, ["*"], false, never>;
}
export declare class SlickItemDirective implements OnInit, OnDestroy {
    private carousel;
    renderer: Renderer2;
    el: ElementRef<any>;
    isServer: boolean;
    ngOnInit(): void;
    ngOnDestroy(): void;
    static ɵfac: i0.ɵɵFactoryDeclaration<SlickItemDirective, never>;
    static ɵdir: i0.ɵɵDirectiveDeclaration<SlickItemDirective, "[ngxSlickItem]", never, {}, {}, never, never, false, never>;
}
