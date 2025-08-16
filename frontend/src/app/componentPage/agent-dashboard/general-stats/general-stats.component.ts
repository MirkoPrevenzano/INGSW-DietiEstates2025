import { Component, Input } from '@angular/core';
import { AgentStats } from '../../../model/response/support/agentStats';

@Component({
  selector: 'app-general-stats',
  imports: [],
  templateUrl: './general-stats.component.html',
  styleUrl: './general-stats.component.scss'
})
export class GeneralStatsComponent{
   
   @Input() generalStats: AgentStats = {
      totalUploadedRealEstates: 0,
      totalRentedRealEstates: 0,
      totalSoldRealEstates: 0,
      rentalsIncome: 0.0,
      salesIncome: 0.0,
    }



   
}
