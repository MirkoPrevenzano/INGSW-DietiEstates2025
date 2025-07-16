import { Component, HostListener, OnInit, ChangeDetectorRef } from '@angular/core';
import { MapComponent } from '../../componentCustom/map/map.component';
import { EstateFiltersComponent } from './estate-filters/estate-filters.component';
import { CommonModule } from '@angular/common';
import { Coordinate } from '../../model/coordinate';
import { MarkerService } from '../../_service/map-service/marker-service/marker.service';
import { Map as LeafletMap } from 'leaflet';
import {MatPaginatorModule, PageEvent} from '@angular/material/paginator';
import { ActivatedRoute, Params } from '@angular/router';
import { CacheEstates } from '../../model/cacheEstates';
import { EstateService } from '../../rest-backend/estates-request/estate.service';
import { EstatePreview } from '../../model/estatePreview';
import { CacheService } from '../../_service/cache-service/cache-service.service';
import { ButtonCustomComponent } from '../../componentCustom/button-custom/button-custom.component';
import { NotFoundComponent } from '../../componentCustom/not-found/not-found.component';
import { FilterService } from '../../_service/filter/filter.service';
import { EstateListComponent } from './estate-list/estate-list.component';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-estate-search-container',
  imports: [
    EstateListComponent,
    MapComponent,
    EstateFiltersComponent,
    CommonModule,
    MatPaginatorModule,
    ButtonCustomComponent,
    NotFoundComponent
  ],
  templateUrl: './estate-search-container.component.html',
  styleUrl: './estate-search-container.component.scss'
})
export class EstateSearchContainerComponent implements OnInit {

  isViewList:boolean=false
  showFilters: boolean = true;
  isMobile: boolean = false;  
  countOfItems: number=0
  pageNumber:number=0
  mapInstance!: LeafletMap
  isPage = false
  notFound404= false


  // Cache con chiave dinamica
  pageCache : Map<string, CacheEstates> = new Map<string, CacheEstates>();

  filter: { [key: string]: any } ={}
  readonly limit: number = 2;

  listCoordinate: Coordinate[] = []
   

  listEstatePreview: EstatePreview[] = []
  listRealEstateId: number[] = []
  constructor(
    private readonly markerService: MarkerService,
    private readonly activadeRouter: ActivatedRoute,
    private readonly estateService: EstateService,
    private readonly cacheService: CacheService,
    private readonly filterService: FilterService,
    private readonly notifyService: ToastrService,
    private readonly cdr: ChangeDetectorRef
  ) {
    this.checkScreenSize();
  }

 

 
  ngOnInit(): void {
    this.activadeRouter.queryParams.subscribe(params => {
      if (!this.isPage) {  // Solo se non è un cambio pagina manuale
        this.retrieveFilter(params)
      }
    });
    
    this.loadEstates();
  }


  retrieveFilter(params: Params) {
    const { filters, page } = this.filterService.retrieveFilter(params);
    this.filter = filters;
    this.pageNumber = page;
  }



  loadEstates() {
    const params = {
      ...this.filter,
      limit: this.limit,
      page: this.pageNumber
    };

    const cacheKey = this.cacheService.getKey(params);
    if (this.pageCache.has(cacheKey)) {
      this.cacheRetrieveEstates(cacheKey) 
    } else {
      this.getEstatesNewFilter(params)
    }
    
    // Reset del flag dopo il caricamento
    this.isPage = false;
  }

  
  private updateViewAndMap() {
    this.markerService.clearMarkers(this.mapInstance);
    if (this.listCoordinate.length > 0 && this.mapInstance) {
      this.markerService.addMarkers(this.listCoordinate, this.mapInstance, this.listRealEstateId);
    }
    this.notFound404 = this.listEstatePreview.length === 0;
    
    // Forza il rilevamento delle modifiche
    this.cdr.detectChanges();
  }

  addPageCache(){
    const result: CacheEstates = {
      listEstatePreview: this.listEstatePreview,
      listCoordinate: this.listCoordinate,
      listRealEstateId: this.listRealEstateId
    };

    this.cacheService.addPageCache(result, this.pageCache, this.filter)
  }
  

  getEstatesNewFilter(params: Params) {
    this.estateService.getEstatesNewFilter(params).subscribe({
      next: (result) =>{
        if(result.realEstatePreviews.length>0){
          this.countOfItems = result.totalElements
          this.listEstatePreview = result.realEstatePreviews;
          this.listCoordinate = result.realEstatePreviews.map((estate:any) => ({
            lat: estate.latitude,
            lon: estate.longitude
          }));
          
          this.listRealEstateId = result.realEstatePreviews.map(
            (estate: EstatePreview) => estate.id
          );
          
          this.updateViewAndMap();
          this.addPageCache()
        }
        else {
          this.listEstatePreview = [];
          this.listCoordinate = [];
          this.listRealEstateId = [];
          this.updateViewAndMap();
        }
      },
      error: (err) => {
        if(err?.error.status >= 400 && err?.error.status < 500)
          this.notifyService.warning(err?.error.description)
        if(err?.error.status >= 500 && err?.error.status < 600)
          this.notifyService.error(err?.error.description)
      }
    })
  }



  cacheRetrieveEstates(cacheKey: string) {
    const cachedData = this.cacheService.getFromCache(cacheKey, this.pageCache);
    if (cachedData) {
      this.listEstatePreview = cachedData.listEstatePreview;
      this.listCoordinate = cachedData.listCoordinate;
      this.listRealEstateId = cachedData.listRealEstateId;
      this.updateViewAndMap();
    }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.checkScreenSize();
  }

  checkScreenSize() {
    this.isMobile = window.innerWidth < 650;
    if (!this.isMobile) {
      this.showFilters = true;
    }
  }

  toggleFilters() {
    this.showFilters = !this.showFilters;
  }

  onMapReady(mapInstance: LeafletMap) {
    this.mapInstance= mapInstance
  }

  onPageChange(event:PageEvent){
    const numberOfPage= event.pageIndex
    this.pageNumber = numberOfPage  // Aggiorna pageNumber PRIMA di tutto
    this.isPage = true
    this.updateUrl({}, numberOfPage)
    this.loadEstates()
  }

  applyFilter(params: Params){
    this.pageCache.clear()
    this.filter = { ...this.filter, ...params };
    this.pageNumber=0
    this.updateUrl(params, 0)
    this.loadEstates()
  }

  updateUrl(params:Params, page:number){
    this.filterService.updateUrl(params, page, this.limit);
  }

  isNotFound() {
    return this.notFound404
  }
}

/*
 con la paginazione è normale caricare solo i post della pagina corrente 
 e rimuovere quelli precedenti per ottimizzare le performance e ridurre 
 il consumo di risorse. */