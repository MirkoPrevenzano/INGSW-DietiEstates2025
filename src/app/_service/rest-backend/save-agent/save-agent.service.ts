import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SavaAgentRequest } from '../model/saveAgentRequest';

@Injectable({
  providedIn: 'root'
})
export class SaveAgentService {

  constructor(private readonly http: HttpClient) { }

  httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

  url="https://localhost:8080/agent/save"

  /*
  *Viene inviato al server un oggetto contenente username e password del nuovo agente
  *Prima cosa bisogna verificare nel backend se l'utente, tramite token, sia valido il token,
  * l'utente sia effettivamente un'amministratore. Si controlla poi se esiste già un agente con tale username.
  * Inoltre si verifica se la passsword è sicura (fatto anche nel frontend)
  * Se la password è sicura e l'username non è già esistente, si genera il nuovo agente
  * altrimenti si invia una response con l'errore evidenziato.
  */
  saveAgent( newAgent: SavaAgentRequest)
  {
    return this.http.post(this.url,newAgent,this.httpOptions)
  }
  
}
