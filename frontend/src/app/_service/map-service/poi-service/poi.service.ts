import { Injectable } from '@angular/core';
import { Coordinate } from '../../../model/coordinate';
import { from, Observable } from 'rxjs';
import { environment } from '../../../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class PoiService {
  private readonly geoapifyKey = environment.geoapifyToken
  private readonly defaultRadius: number = 1000;


  searchPOI(coordinate: Coordinate, categories: string[], radius: number = this.defaultRadius): Observable<any> {
    const url = `https://api.geoapify.com/v2/places?categories=${categories.join(',')}&filter=circle:${coordinate.lon},${coordinate.lat},${radius}&bias=proximity:${coordinate.lon},${coordinate.lat}&limit=20&apiKey=${this.geoapifyKey}`;
    
    return from(fetch(url).then(response => response.json()));
  }
}
