import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AgentRecentRealEstate } from '../../model/response/agentRecentRealEstate';
import { AgentStats } from '../../model/response/support/agentStats';
import { AgentDashboardRealEstateStats } from '../../model/response/agentDashboardRealEstateStats';
import { AgentDashboardComponent } from '../../componentPage/agent-dashboard/agent-dashboard.component';
import { AgentDashboardPersonalStats } from '../../model/response/agentDashboardPersonalStats';

@Injectable({
  providedIn: 'root'
})
export class AgentService {

  constructor(private readonly http: HttpClient) { }
  
    private httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      };
  
    private url="http://localhost:8080/agents"

    recentlyRealEstate(user:string):Observable<AgentRecentRealEstate[]>{
      const url= this.url+`/recent-real-estates/4`
      return this.http.get<AgentRecentRealEstate[]>(url,this.httpOptions)
    }


    /*
      Richiedo il numero di operazioni completate da un'agente, per ogni mese.
      Nel body va un vettore di interi di 12 celle, ogni cella corrisponde ad un mese.
      url: /agent/{username}/number-closed-estate
    */

    numberOfClosedEstate(user:string):Observable<number[]>{
      const url = this.url+`/${user}/number-closed-estate`
      return this.http.get<number[]>(url, this.httpOptions)
    }

    /*
      Richiesto le statistiche generali di un agente immobiliare.
      Mi aspetto un oggetto AgentGeneralStats che ha i seguenti attributi:
      -uploadedNumber
      -soldEstates
      -rentedEstates
      -salesIncome
      -rentalsIncome

      url: agent/{username}/general-stats
    */
    agentStats(user:string):Observable<AgentDashboardPersonalStats>{
      const url = this.url+`/dashboard/personal-stats`
      return this.http.get<AgentDashboardPersonalStats>(url, this.httpOptions)
    }


    /*
      Richiesto le statistiche per ogni immobile di un certo agente immobiliare.
      Passo come valore di query, il numero di pagina e limit
      Mi aspetto un vettore di oggetti EstateStats che ha i seguenti attributi:
      -title
      -uploadDate
      -offerNumber
      -viewNumber
      -id (l'id dell'estate, serve perchè cliccando sulla riga della tabella ti reindirizza nel dettaglio dell'estate)

      url: agent/{username}/estates-stats?page=${page}&limit=${limit}`
    */
    estatesStats(user:string, page:number, limit:number):Observable<AgentDashboardRealEstateStats[]>{
      const url = `${this.url}/dashboard/estates-stats/${page}/${limit}`
      return this.http.get<AgentDashboardRealEstateStats[]>(url, this.httpOptions)
    }


    exportCSV():Observable<Blob>{
      const url = this.url+`/dashboard/csv-report`;
      return this.http.get(url, {responseType:'blob'})
  
    }

    exportPDF():Observable<Blob>{
      const url = this.url+`/dashboard/pdf-report`;
      return this.http.get(url, {responseType:'blob'})
    }
  
}
