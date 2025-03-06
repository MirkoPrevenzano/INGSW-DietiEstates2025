import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PasswordRequest } from '../../../model/passwordRequest';

@Injectable({
  providedIn: 'root'
})
export class PasswordChangeService {

  constructor(private readonly http:HttpClient) { }
  
    url:string="http://localhost:8080/admin"
  
    httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
    };

    /*
    *PasswordRequest ha tre parametri, vecchia e nuova password e username dell'admin. Nel backend si deve controllare se il token
    *di autenticazione passato nella request è valido, bisogna controllare se l'user ricavato dal token, corrisponde all'user presente
    *nel body. Bisogna controllare se la vecchia password corrisponde a quella reale. Bisogna testare se la password è sicura (doppio controllo,
    *tale controllo è fatto in primis nel frontend).
    *Se una di tali condizioni non è valida bisogna inviare nella response il messaggio preciso di errore associato al codice corretto
    * 
    */
    passwordChange(request: PasswordRequest){
      const url= this.url+`/${localStorage.getItem('user')}/update-password`
      return this.http.put(url, request, this.httpOptions);
    }
}
