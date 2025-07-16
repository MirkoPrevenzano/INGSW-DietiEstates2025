import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { EstateRecently } from '../../../model/estateRecently';
import { AgentService } from '../../../rest-backend/agent/agent.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-recently-estate-popup',
  imports: [CommonModule],
  templateUrl: './recently-estate-popup.component.html',
  styleUrl: './recently-estate-popup.component.scss'
})
export class RecentlyEstatePopupComponent {
  recentListings: EstateRecently[] = []; 

  constructor(
    private readonly agentService: AgentService,
    private readonly notifyService: ToastrService
  ){}
  
  ngOnInit(): void {
    const user = localStorage.getItem('user');
    if(user)
      this.agentService.recentlyRealEstate(user).subscribe({
        next: (result) =>{
          this.recentListings = result
        },
        error: (err) => {
        if(err?.error.status >= 400 && err?.error.status < 500)
          this.notifyService.warning(err?.error.description)
        if(err?.error.status >= 500 && err?.error.status < 600)
          this.notifyService.error(err?.error.description)
        }
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

