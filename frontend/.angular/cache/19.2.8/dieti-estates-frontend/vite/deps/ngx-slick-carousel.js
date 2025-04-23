import {
  NG_VALUE_ACCESSOR
} from "./chunk-SNTO3LF4.js";
import {
  CommonModule
} from "./chunk-6SMMMRAD.js";
import {
  isPlatformServer
} from "./chunk-YX4F4KHC.js";
import {
  Component,
  Directive,
  ElementRef,
  Input,
  NgModule,
  NgZone,
  PLATFORM_ID,
  Renderer2,
  forwardRef,
  inject,
  output,
  setClassMetadata,
  ɵɵNgOnChangesFeature,
  ɵɵProvidersFeature,
  ɵɵdefineComponent,
  ɵɵdefineDirective,
  ɵɵdefineInjector,
  ɵɵdefineNgModule,
  ɵɵprojection,
  ɵɵprojectionDef
} from "./chunk-2T5GRGGG.js";
import "./chunk-P6U2JBMQ.js";
import "./chunk-IMJAHVCN.js";

// node_modules/ngx-slick-carousel/fesm2022/ngx-slick-carousel.mjs
var _c0 = ["*"];
var SlickCarouselComponent = class _SlickCarouselComponent {
  config;
  afterChange = output();
  beforeChange = output();
  breakpoint = output();
  destroy = output();
  init = output();
  $instance;
  // access from parent component can be a problem with change detection timing. Please use afterChange output
  currentIndex = 0;
  slides = [];
  initialized = false;
  _removedSlides = [];
  _addedSlides = [];
  el = inject(ElementRef);
  zone = inject(NgZone);
  isServer = isPlatformServer(inject(PLATFORM_ID));
  /**
   * On component destroy
   */
  ngOnDestroy() {
    this.unslick();
  }
  ngAfterViewInit() {
    this.ngAfterViewChecked();
  }
  /**
   * On component view checked
   */
  ngAfterViewChecked() {
    if (this.isServer) {
      return;
    }
    if (this._addedSlides.length > 0 || this._removedSlides.length > 0) {
      const nextSlidesLength = this.slides.length - this._removedSlides.length + this._addedSlides.length;
      if (!this.initialized) {
        if (nextSlidesLength > 0) {
          this.initSlick();
        }
      } else if (nextSlidesLength === 0) {
        this.unslick();
      } else {
        this._addedSlides.forEach((slickItem) => {
          this.slides.push(slickItem);
          this.$instance.slick("slickAdd", slickItem.el.nativeElement);
        });
        this._addedSlides = [];
        this._removedSlides.forEach((slickItem) => {
          const idx = this.slides.indexOf(slickItem);
          this.slides = this.slides.filter((s) => s !== slickItem);
          this.$instance.slick("slickRemove", idx);
        });
        this._removedSlides = [];
      }
    }
  }
  /**
   * init slick
   */
  initSlick() {
    this.slides = this._addedSlides;
    this._addedSlides = [];
    this._removedSlides = [];
    this.$instance = jQuery(this.el.nativeElement);
    this.$instance.on("init", (event, slick) => {
      this.zone.run(() => {
        this.init.emit({
          event,
          slick
        });
      });
    });
    this.$instance.slick(this.config);
    this.zone.run(() => {
      this.initialized = true;
      this.currentIndex = this.config?.initialSlide || 0;
    });
    this.$instance.on("afterChange", (event, slick, currentSlide) => {
      this.zone.run(() => {
        this.afterChange.emit({
          event,
          slick,
          currentSlide,
          first: currentSlide === 0,
          last: slick.$slides.length === currentSlide + slick.options.slidesToScroll
        });
        this.currentIndex = currentSlide;
      });
    });
    this.$instance.on("beforeChange", (event, slick, currentSlide, nextSlide) => {
      this.zone.run(() => {
        this.beforeChange.emit({
          event,
          slick,
          currentSlide,
          nextSlide
        });
        this.currentIndex = nextSlide;
      });
    });
    this.$instance.on("breakpoint", (event, slick, breakpoint) => {
      this.zone.run(() => {
        this.breakpoint.emit({
          event,
          slick,
          breakpoint
        });
      });
    });
    this.$instance.on("destroy", (event, slick) => {
      this.zone.run(() => {
        this.destroy.emit({
          event,
          slick
        });
        this.initialized = false;
      });
    });
  }
  addSlide(slickItem) {
    this._addedSlides.push(slickItem);
  }
  removeSlide(slickItem) {
    this._removedSlides.push(slickItem);
  }
  /**
   * Slick Method
   */
  slickGoTo(index) {
    this.$instance.slick("slickGoTo", index);
  }
  slickNext() {
    this.$instance.slick("slickNext");
  }
  slickPrev() {
    this.$instance.slick("slickPrev");
  }
  slickPause() {
    this.$instance.slick("slickPause");
  }
  slickPlay() {
    this.$instance.slick("slickPlay");
  }
  unslick() {
    if (this.$instance) {
      this.$instance.slick("unslick");
      this.$instance = void 0;
    }
    this.initialized = false;
  }
  ngOnChanges(changes) {
    if (this.initialized) {
      const config = changes["config"];
      if (config.previousValue !== config.currentValue && config.currentValue !== void 0) {
        const refresh = config.currentValue["refresh"];
        const newOptions = Object.assign({}, config.currentValue);
        delete newOptions["refresh"];
        this.$instance.slick("slickSetOption", newOptions, refresh);
      }
    }
  }
  /** @nocollapse */
  static ɵfac = function SlickCarouselComponent_Factory(__ngFactoryType__) {
    return new (__ngFactoryType__ || _SlickCarouselComponent)();
  };
  /** @nocollapse */
  static ɵcmp = ɵɵdefineComponent({
    type: _SlickCarouselComponent,
    selectors: [["ngx-slick-carousel"]],
    inputs: {
      config: "config"
    },
    outputs: {
      afterChange: "afterChange",
      beforeChange: "beforeChange",
      breakpoint: "breakpoint",
      destroy: "destroy",
      init: "init"
    },
    exportAs: ["slick-carousel"],
    standalone: false,
    features: [ɵɵProvidersFeature([{
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => _SlickCarouselComponent),
      multi: true
    }]), ɵɵNgOnChangesFeature],
    ngContentSelectors: _c0,
    decls: 1,
    vars: 0,
    template: function SlickCarouselComponent_Template(rf, ctx) {
      if (rf & 1) {
        ɵɵprojectionDef();
        ɵɵprojection(0);
      }
    },
    encapsulation: 2
  });
};
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(SlickCarouselComponent, [{
    type: Component,
    args: [{
      selector: "ngx-slick-carousel",
      exportAs: "slick-carousel",
      providers: [{
        provide: NG_VALUE_ACCESSOR,
        useExisting: forwardRef(() => SlickCarouselComponent),
        multi: true
      }],
      template: "<ng-content></ng-content>",
      standalone: false
    }]
  }], null, {
    config: [{
      type: Input
    }]
  });
})();
var SlickItemDirective = class _SlickItemDirective {
  carousel = inject(SlickCarouselComponent, {
    host: true
  });
  renderer = inject(Renderer2);
  el = inject(ElementRef);
  isServer = isPlatformServer(inject(PLATFORM_ID));
  ngOnInit() {
    this.carousel.addSlide(this);
    if (this.isServer && this.carousel.slides.length > 0) {
      this.renderer.setStyle(this.el, "display", "none");
    }
  }
  ngOnDestroy() {
    this.carousel.removeSlide(this);
  }
  /** @nocollapse */
  static ɵfac = function SlickItemDirective_Factory(__ngFactoryType__) {
    return new (__ngFactoryType__ || _SlickItemDirective)();
  };
  /** @nocollapse */
  static ɵdir = ɵɵdefineDirective({
    type: _SlickItemDirective,
    selectors: [["", "ngxSlickItem", ""]],
    standalone: false
  });
};
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(SlickItemDirective, [{
    type: Directive,
    args: [{
      selector: "[ngxSlickItem]",
      standalone: false
    }]
  }], null, null);
})();
var SlickCarouselModule = class _SlickCarouselModule {
  /** @nocollapse */
  static ɵfac = function SlickCarouselModule_Factory(__ngFactoryType__) {
    return new (__ngFactoryType__ || _SlickCarouselModule)();
  };
  /** @nocollapse */
  static ɵmod = ɵɵdefineNgModule({
    type: _SlickCarouselModule,
    declarations: [SlickCarouselComponent, SlickItemDirective],
    imports: [CommonModule],
    exports: [SlickCarouselComponent, SlickItemDirective]
  });
  /** @nocollapse */
  static ɵinj = ɵɵdefineInjector({
    imports: [CommonModule]
  });
};
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(SlickCarouselModule, [{
    type: NgModule,
    args: [{
      imports: [CommonModule],
      declarations: [SlickCarouselComponent, SlickItemDirective],
      exports: [SlickCarouselComponent, SlickItemDirective]
    }]
  }], null, null);
})();
export {
  SlickCarouselComponent,
  SlickCarouselModule,
  SlickItemDirective
};
//# sourceMappingURL=ngx-slick-carousel.js.map
