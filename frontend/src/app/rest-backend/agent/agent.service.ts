import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AgentRecentRealEstate } from '../../model/response/agentRecentRealEstate';
import { AgentStats } from '../../model/response/support/agentStats';
import { AgentDashboardRealEstateStats } from '../../model/response/agentDashboardRealEstateStats';
import { AgentDashboardComponent } from '../../componentPage/agent-dashboard/agent-dashboard.component';
import { AgentDashboardPersonalStats } from '../../model/response/agentDashboardPersonalStats';
import { AgentCreation } from '../../model/request/agentCreation';
import { AgentPublicInfo } from '../../model/response/support/agentPublicInfo';

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


    saveAgent( newAgent: AgentCreation)
    {
      return this.http.post(this.url,newAgent,this.httpOptions)
    }

    recentlyRealEstate():Observable<AgentRecentRealEstate[]>{
      const url= this.url+`/recent-real-estates/4`
      return this.http.get<AgentRecentRealEstate[]>(url,this.httpOptions)
    }


    getAgentPublicInfo():Observable<AgentPublicInfo>{
      const url = this.url+`/public-info`
      return this.http.get<AgentPublicInfo>(url, this.httpOptions)
    }
   
    agentStats():Observable<AgentDashboardPersonalStats>{
      const url = this.url+`/dashboard/personal-stats`
      return this.http.get<AgentDashboardPersonalStats>(url, this.httpOptions)
    }


    
    estatesStats(page: number, limit: number):Observable<AgentDashboardRealEstateStats[]>{
      const url = `${this.url}/dashboard/real-estate-stats/${page}/${limit}`
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
