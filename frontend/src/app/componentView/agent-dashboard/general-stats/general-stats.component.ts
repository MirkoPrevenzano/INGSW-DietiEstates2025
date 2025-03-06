import { Component, Input } from '@angular/core';
import { AgentGeneralStats } from '../../../model/agentGeneralStats';

@Component({
  selector: 'app-general-stats',
  imports: [],
  templateUrl: './general-stats.component.html',
  styleUrl: './general-stats.component.scss'
})
export class GeneralStatsComponent{
   
   @Input() generalStats: AgentGeneralStats = {
      totalUploadedRealEstates: 0,
      totalRentedRealEstates: 0,
      totalSoldRealEstates: 0,
      totalIncome: 0.0,
      rentalsIncome: 0.0,
      salesIncome: 0.0,
      successRate: 0.0,
      salesToRentalsRatio:0.0,
      rentalToSalesRatio: 0.0,
    }



   
}
