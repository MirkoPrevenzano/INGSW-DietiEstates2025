import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AgentRecentRealEstate } from '../../../model/response/agentRecentRealEstate';
import { AgentService } from '../../../rest-backend/agent/agent.service';
import { ToastrService } from 'ngx-toastr';
import { HandleNotifyService } from '../../../_service/handle-notify.service';

@Component({
  selector: 'app-recently-estate-popup',
  imports: [CommonModule, RouterLink],
  templateUrl: './recently-estate-popup.component.html',
  styleUrl: './recently-estate-popup.component.scss'
})
export class RecentlyEstatePopupComponent {
  recentListings: AgentRecentRealEstate[] = []; 

  constructor(
    private readonly agentService: AgentService,
    private readonly handleError: HandleNotifyService
  ){}
  
  ngOnInit(): void {
    const user = localStorage.getItem('user');
    if(user)
      this.agentService.recentlyRealEstate(user).subscribe({
        next: (result) =>{
          this.recentListings = result
        },
        error: (err) => {
          this.handleError.showMessageError(err.error)
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
  
  
}

