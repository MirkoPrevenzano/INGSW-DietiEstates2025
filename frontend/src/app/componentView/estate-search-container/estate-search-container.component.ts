import { Component, HostListener, OnInit } from '@angular/core';
import { EstateListComponent } from '../estate-list/estate-list.component';
import { MapComponent } from '../map/map.component';
import { EstateFiltersComponent } from '../estate-filters/estate-filters.component';
import { CommonModule } from '@angular/common';
import { Coordinate } from '../../model/coordinate';
import { MarkerService } from '../../_service/map-service/marker-service/marker.service';
import { Map as LeafletMap } from 'leaflet';
import {MatPaginatorModule, PageEvent} from '@angular/material/paginator';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { CacheEstates } from '../../model/cacheEstates';
import { EstateService } from '../../_service/rest-backend/estates-request/estate.service';
import { forkJoin, from, mergeMap, switchMap } from 'rxjs';
import { EstatePreview } from '../../model/estatePreview';
import { CacheService } from '../../_service/cache-service/cache-service.service';
import { ButtonCustomComponent } from '../button-custom/button-custom.component';
import { NotFoundComponent } from '../not-found/not-found.component';
import { UploadPhotoService } from '../../_service/rest-backend/upload-photo/upload-photo.service';


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
  readonly maxCacheSize: number = 5;
  mapInstance!: LeafletMap
  isPage = false
  notFound404= false


  // Cache con chiave dinamica
  pageCache : Map<string, CacheEstates> = new Map<string, CacheEstates>();

  filter: { [key: string]: any } ={}
  readonly limit: number = 2;

  listCoordinate: Coordinate[] = []
   

  listPhotos: string[][] = []
  listEstatePreview: EstatePreview[] = []
  listRealEstateId: number[] = []
  constructor(
    private readonly markerService: MarkerService,
    private readonly router: Router,
    private readonly activadeRouter: ActivatedRoute,
    private readonly estateService: EstateService,
    private readonly cacheService: CacheService,
    private readonly uploadPhotoService: UploadPhotoService
  ) {
    this.checkScreenSize();
  }

 

  /*
    gestisco query
  */
  ngOnInit(): void {
    this.activadeRouter.queryParams.subscribe(params => {
      this.retrieveFilter(params)
    });

    this.loadEstates();
  }

  /*
    ricavo filtri e li inserisco nell'url
   */
  retrieveFilter(params: Params) {
    this.pageNumber = Number(params['page']) || 0
      
      if(!params['page']){
        const page = this.pageNumber
        const limit = this.limit
        this.router.navigate([],{
          relativeTo: this.activadeRouter,
          queryParams: {...params, page,limit},
          queryParamsHandling: 'merge'
        })
      }

      this.filter={...params}
      delete this.filter['page']
      delete this.filter['limit']
  }


  
  /*
    ricavo gli estates
  */
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
      this.serverRetrieveEstates(params,cacheKey)
    }


    
  }

  
  retrievePhotos() {
    this.listPhotos=[]
    from(this.listRealEstateId)
      .pipe(
        mergeMap(id => this.uploadPhotoService.getPhotos(id), 5) // Limita a 5 richieste simultanee
      )
      .subscribe({
        next: (photos: string[]) => {
          
          this.listPhotos.push(photos.map(photo => `data:image/jpeg;base64,${photo}`));
          console.log('listPhotos:', this.listPhotos);
        },
        error: (error) => {
          console.error('Errore nel recupero delle foto:', error);
        }
      });
  }


 

  

  
  addPageCache(){
    const result: CacheEstates = {
      listEstatePreview: this.listEstatePreview,
      listCoordinate: this.listCoordinate,
      listRealEstateId: this.listRealEstateId
    };

    this.cacheService.addPageCache(result, this.pageCache, this.filter)
  }
  


  /*Ricavo gli estates dal server */
  serverRetrieveEstates(params: { [key: string]: any; }, cacheKey: string) {
    if(this.isPage)
      this.getEstatesNewPage(params,cacheKey)
    else
      this.getEstatesNewFilter(params,cacheKey)  
  }

  /*richiesta estates per cambio di pagina */
  getEstatesNewPage(params: { [key: string]: any; }, cacheKey: string) {
    console.log("estateNewPage")
    console.log(params)
    this.estateService.getEstatesNewPage(params).subscribe((result) => {
        if (!result) {
          console.error("Result is undefined");
          return;
        }
  
        console.log("risultato");
        console.log(result);
        this.listEstatePreview = result;
        this.listCoordinate = result.map((estate: any) => ({
          lat: estate.latitude,
          lon: estate.longitude
        }));
  
        this.listRealEstateId = result.map(
          (estate: EstatePreview) => estate.id
        );
  
        this.isPage = false;
  
        this.markerService.addMarkers(this.listCoordinate, this.mapInstance, this.listRealEstateId);
        this.addPageCache();
        this.retrievePhotos()
      });
    
  }
  
  getEstatesNewFilter(params: { [key: string]: any; }, cacheKey: string) {
    console.log("estateNewFilter")
    console.log(params)
    this.estateService.getEstatesNewFilter(params).subscribe((result:any)=>{
        console.log(result.totalElements)
        if(result.totalElements>0){
          this.countOfItems = result.totalElements
          this.listEstatePreview = result.realEstatePreviews;
          this.listCoordinate = result.realEstatePreviews.map((estate:any) => ({
            lat: estate.latitude,
            lon: estate.longitude
          }));
          
          this.listRealEstateId = result.realEstatePreviews.map(
            (estate: EstatePreview) => estate.id
          );
          
          this.retrievePhotos()
          this.markerService.addMarkers(this.listCoordinate, this.mapInstance, this.listRealEstateId);
          this.addPageCache()
        }
        else
          this.notFound404=true
      
      })
  }



  cacheRetrieveEstates(cacheKey: string) {
      // Usa i dati dalla cache
      const cachedData = this.pageCache.get(cacheKey);
      this.listEstatePreview = cachedData!.listEstatePreview
      this.listCoordinate = cachedData!.listCoordinate
      this.listRealEstateId = cachedData!.listRealEstateId
      this.markerService.addMarkers(this.listCoordinate, this.mapInstance, this.listRealEstateId)
        
      this.retrievePhotos()

         
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
    this.isPage = true
    this.updateUrl({}, numberOfPage)
    this.loadEstates()
  }

  applyFilter(params: { [key: string]: any }){
    this.pageCache.clear()
    this.updateUrl(params, 0)
    this.loadEstates()

  }

  updateUrl(params:{ [key: string]: any }, page:number){
    this.pageNumber = page
    
    const queryParams: { [key: string]: any } = {
      ...params,
      page:this.pageNumber,
      limit:this.limit
    }


    this.router.navigate([],{
      relativeTo:this.activadeRouter,
      queryParams: queryParams,
      queryParamsHandling: 'merge'
    })
  }

  isNotFound() {
    return this.notFound404
  }
}

/*
 con la paginazione è normale caricare solo i post della pagina corrente 
 e rimuovere quelli precedenti per ottimizzare le performance e ridurre 
 il consumo di risorse. */