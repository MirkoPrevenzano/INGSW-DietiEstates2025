import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild } from '@angular/core';
import { Map,} from 'leaflet';
import { Coordinate } from '../../model/coordinate';
import { EstateLocationFeatures } from '../../model/estateLocationFeatures';
import { MapService } from '../../_service/map-service/map.service';
import { CommonModule } from '@angular/common';

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
  @Output() nearbyPOI = new EventEmitter<EstateLocationFeatures>()
  @Output() mapReady = new EventEmitter<Map>();

  private readonly geoapifyKey = 'c0324c2c2afb488980eae981c7906a43';
  private mapInstance!:Map

  constructor(
    private readonly mapService: MapService,

  ) {}

  ngAfterViewInit() {
    const initCoord: Coordinate ={lat:11, lon:49}
    const initZoom = 4
    this.mapInstance=this.mapService.initMap(this.mapElement,initCoord,initZoom)
    if(this.clickable){
      this.mapService.onClickMap((coordinate:Coordinate)=>{
        this.addMarker(coordinate)
      })
    }

    if (this.coordinates) {
      this.addMarker(this.coordinates);
    }
    this.mapReady.emit(this.mapInstance)
   

  }

  addMarker(coordinates: Coordinate){
    if(this.radius)
      this.mapService.addMarkerWithRadius(coordinates, this.radius)
    else
      this.mapService.addMarker(coordinates)
    this.coordinateWithMarker.emit(coordinates)
  }

  


  ngOnChanges(changes: SimpleChanges): void {
    if(this.mapElement && 
        (changes['coordinates'] || changes['radius']) && 
        this.coordinates
    )
      this.addMarker(this.coordinates)
    
  }

 
  
  
}