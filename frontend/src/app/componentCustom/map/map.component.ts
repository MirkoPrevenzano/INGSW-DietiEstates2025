import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild } from '@angular/core';
import { Map,} from 'leaflet';
import { Coordinate } from '../../model/coordinate';
import { RealEstateLocationFeatures } from '../../model/request/support/realEstateLocationFeatures';
import { MapService } from '../../_service/map-service/map.service';
import { CommonModule } from '@angular/common';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  imports:[CommonModule],
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterViewInit, OnChanges {
  @ViewChild('map') private readonly mapElement!: ElementRef<HTMLElement>
  @Input() coordinates?: Coordinate
  @Input() listCoordinate: Coordinate[] = []
  @Input() width: string = '100%'; 
  @Input() height: string = '100%';
  @Input() radius?: number
  @Input() clickable:boolean = false
  @Output() coordinateWithMarker = new EventEmitter<Coordinate>()
  @Output() nearbyPOI = new EventEmitter<RealEstateLocationFeatures>()
  @Output() mapReady = new EventEmitter<Map>();

  private readonly geoapifyKey = environment.geoapifyToken;
  private mapInstance!:Map

  constructor(
    private readonly mapService: MapService,

  ) {}

  ngAfterViewInit() {
    try {
      const initCoord: Coordinate = {lat:40.837726396538166, lon:14.306795597076418};
      const initZoom = 4;
      this.mapInstance = this.mapService.initMap(this.mapElement, initCoord, initZoom);
      
      if(this.clickable){
        this.mapService.onClickMap((coordinate:Coordinate)=>{
          this.addMarker(coordinate);
        });
      }

      // Add initial marker if coordinates are provided
      if (this.coordinates) {
        this.addMarker(this.coordinates);
      }
      
      this.mapReady.emit(this.mapInstance);
    } catch (error) {
      console.error('Error initializing map:', error);
    }
  }

  addMarker(coordinates: Coordinate){
    if(this.radius)
      this.mapService.addMarkerWithRadius(coordinates, this.radius)
    else
      this.mapService.addMarker(coordinates)
    this.coordinateWithMarker.emit(coordinates)
  }

  


  ngOnChanges(changes: SimpleChanges): void {
    if(this.mapInstance && 
        (changes['coordinates'] || changes['radius']) && 
        this.coordinates
    ) {
      try {
        this.addMarker(this.coordinates);
      } catch (error) {
        console.error('Error adding marker on coordinate change:', error);
      }
    }
  }

 
  
  
}