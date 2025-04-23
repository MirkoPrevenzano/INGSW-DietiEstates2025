import { ElementRef, Injectable } from '@angular/core';
import { Browser, map, Map, tileLayer } from 'leaflet';
import { Coordinate } from '../../model/coordinate';
import { MarkerService } from './marker-service/marker.service';
import { environment } from '../../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class MapService {
  private readonly geoapifyKey = environment.geoapifyToken
  private mapInstance!: Map


  constructor(
    private readonly markerService: MarkerService
  ) { }

  initMap(mapElement: ElementRef<HTMLElement>, initCoord: Coordinate, initZoom: number): Map{
    this.mapInstance = map(mapElement.nativeElement).setView(
      [initCoord.lat, initCoord.lon], 
      initZoom
    )

    const isRetina = Browser.retina;
    const baseUrl =
      `https://maps.geoapify.com/v1/tile/osm-bright/{z}/{x}/{y}.png?apiKey=${this.geoapifyKey}`;
    const retinaUrl =
      `https://maps.geoapify.com/v1/tile/osm-bright/{z}/{x}/{y}@2x.png?apiKey=${this.geoapifyKey}`;
      
    tileLayer(isRetina ? retinaUrl : baseUrl, {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(this.mapInstance);
    
    setTimeout(() => {
      this.mapInstance.invalidateSize();
    }, 100);

    return this.mapInstance
  }

  addMarker(coordinate: Coordinate) {
    this.checkInstance();
    this.markerService.addMarker(coordinate,this.mapInstance);
  }

  addMarkerWithRadius(coordinate: Coordinate, radius:number) {
    this.checkInstance();
    this.markerService.addMarkerWithRadius(coordinate,radius,this.mapInstance);
  }

 


  
  onClickMap(callback: (coordinate: Coordinate)=> void):void{
    this.checkInstance()  

    this.mapInstance.on('click', (event:any)=>{
      callback({lat:event.latlng.lat, lon:event.latlng.lng})
    });
  }

  checkInstance(){
    if (!this.mapInstance) {
      console.error('Map instance is not initialized');
    }
  }

  
}
