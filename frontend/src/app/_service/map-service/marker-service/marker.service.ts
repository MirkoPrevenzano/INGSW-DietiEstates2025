import { Injectable } from '@angular/core';
import { circle, Layer, Map, marker, Marker, PopupOptions } from 'leaflet';
import { Coordinate } from '../../../model/coordinate';
import {  Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MarkerService {
  private markerInstance!: Layer
  private markers: Marker<any>[] = [];



  constructor(private readonly router:Router) { }
  addMarkers(coordinates: Coordinate[], mapInstance: Map, realEstateIds:number[]){
    this.clearMarkers(mapInstance);
      coordinates.forEach((coordinate,index)=>{
        
        const newMarker = marker([coordinate.lat, coordinate.lon])
        setTimeout(() => {
          const popupContent = document.getElementById('estate-preview-' + realEstateIds[index]);
          if (popupContent) {
            const popupOptions: PopupOptions = {
              maxWidth: 600,
              minWidth: 400,
              maxHeight: 600,
              autoPan: true,
              keepInView: true,
              autoClose: true,
              className: 'custom-popup'
            };
            newMarker.bindPopup(popupContent.innerHTML, popupOptions);
          }
          this.markerInstance = newMarker.addTo(mapInstance);
          this.markers.push(newMarker); 

        }, 500);

        this.setEventOnClick(newMarker, realEstateIds[index])
        
      })
  
      if(coordinates.length>0)
      {
        mapInstance.setView([
          coordinates[0].lat,
          coordinates[0].lon
        ],20)
      }
  }

  clearMarkers(mapInstance: Map) {
    this.markers.forEach(marker => {
      mapInstance.removeLayer(marker);
    });
    this.markers = [];
  }

  setEventOnClick(newMarker: Marker<any>, realEstateId: number) {
    newMarker.on('popupopen', () => {
      const popupElement = document.querySelector('.leaflet-popup-content');
      if (popupElement) {
        popupElement.addEventListener('click', () => {
          this.router.navigate([`/estate/${realEstateId}`]);
        });
      }
    });
  }

  addMarker(coordinate: Coordinate, mapInstance:Map){
    if(this.markerInstance)
      mapInstance.removeLayer(this.markerInstance)

    this.markerInstance = marker([
      coordinate.lat,
      coordinate.lon
    ]).addTo(mapInstance)

    mapInstance.setView([
      coordinate.lat,
      coordinate.lon
    ], 20)
  }

  addMarkerWithRadius(coordinate:Coordinate, radius:number, mapInstance:Map){
    if(this.markerInstance)
      mapInstance.removeLayer(this.markerInstance)

    this.markerInstance = circle([
      coordinate.lat,
      coordinate.lon],
      {
        radius: radius,
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5
      }
    ).addTo(mapInstance)

    mapInstance.setView([
      coordinate.lat,
      coordinate.lon
    ], 12)
  }

  
  
}
