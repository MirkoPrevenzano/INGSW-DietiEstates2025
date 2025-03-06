import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Coordinate } from '../../../model/coordinate';
import { from, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PoiService {
  private readonly geoapifyKey = 'c0324c2c2afb488980eae981c7906a43'; // Sostituisci con la tua chiave API di Geoapify
  private readonly defaultRadius: number = 1000;

  constructor(private readonly http: HttpClient) { }

  searchPOI(coordinate: Coordinate, categories: string[], radius: number = this.defaultRadius): Observable<any> {
    const url = `https://api.geoapify.com/v2/places?categories=${categories.join(',')}&filter=circle:${coordinate.lon},${coordinate.lat},${radius}&bias=proximity:${coordinate.lon},${coordinate.lat}&limit=20&apiKey=${this.geoapifyKey}`;
    
    return from(fetch(url).then(response => response.json()));
  }
}
