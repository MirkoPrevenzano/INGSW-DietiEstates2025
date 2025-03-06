import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { EstateRecently } from '../../model/estateRecently';
import { AgentService } from '../../_service/rest-backend/agent/agent.service';

@Component({
  selector: 'app-recently-estate-popup',
  imports: [CommonModule],
  templateUrl: './recently-estate-popup.component.html',
  styleUrl: './recently-estate-popup.component.scss'
})
export class RecentlyEstatePopupComponent {
  recentListings: EstateRecently[] = []; 

  constructor(private readonly agentService: AgentService){}
  
  ngOnInit(): void {
    const user = localStorage.getItem('user');
    this.agentService.recentyRealEstate(user!).subscribe( result=>{
      this.recentListings = result
    })
  }

  truncate(text: string, limit: number): string {
    if (!text) return '';
    const words = text.split(' ');
    if (words.length > limit) {
      return words.slice(0, limit).join(' ') + '...';
    }
    return text;
  }
  
  goToEstateDetail(id:number){
    console.log(id)
  }
}

