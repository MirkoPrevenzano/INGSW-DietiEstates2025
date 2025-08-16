import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AgentCreation } from '../../model/request/agentCreation';
import { CollaboratorCreation } from '../../model/request/collaboratorCreation';

@Injectable({
  providedIn: 'root'
})
export class CreateStaffService {

  constructor(private readonly http: HttpClient) { }

  private httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

  private url="http://localhost:8080/admin"

  /*
  *Viene inviato al server un oggetto contenente username e password del nuovo agente
  *Prima cosa bisogna verificare nel backend se l'utente, tramite token, sia valido il token,
  * l'utente sia effettivamente un'amministratore. Si controlla poi se esiste già un agente con tale username.
  * Inoltre si verifica se la passsword è sicura (fatto anche nel frontend)
  * Se la password è sicura e l'username non è già esistente, si genera il nuovo agente
  * altrimenti si invia una response con l'errore evidenziato.
  */
  saveAgent( newAgent: AgentCreation)
  {
    const url= this.url+`/${localStorage.getItem("user")}/create-agent`
    return this.http.post(url,newAgent,this.httpOptions)
  }

  saveAdmin( newAdmin: CollaboratorCreation)
  {
    const url= this.url+`/${localStorage.getItem("user")}/create-collaborator`
    return this.http.post(url,newAdmin,this.httpOptions)
  }
  
}
