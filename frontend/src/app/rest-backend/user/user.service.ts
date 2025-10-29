import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RealEstateCreation } from '../../model/request/realEstateCreation';
import { UpdatePassword } from '../../model/request/updatePassword';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly url = `${environment.apiBaseUrl}/users`;

  constructor(private readonly http: HttpClient) {}

  private readonly httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  updatePassword(password: UpdatePassword): Observable<any> {
    const url = `${this.url}/password`;
    return this.http.put(url, password, this.httpOptions);
  }
}
