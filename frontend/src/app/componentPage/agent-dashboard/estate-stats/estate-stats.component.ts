import { AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { AgentService } from '../../../rest-backend/agent/agent.service';
import { AgentDashboardRealEstateStats } from '../../../model/response/agentDashboardRealEstateStats';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { HandleNotifyService } from '../../../_service/handle-notify.service';

@Component({
  selector: 'app-estate-stats',
  imports: [
    RouterLink,
    CommonModule
  ],
  templateUrl: './estate-stats.component.html',
  styleUrl: './estate-stats.component.scss'
})
export class EstateStatsComponent implements OnInit, AfterViewInit, OnDestroy{
  page:number = 0
  limit: number = 10
  loading: boolean = false; //evito richieste multiple
  @ViewChild('scrollContainer') scrollContainer!: ElementRef;



  estatesStats: AgentDashboardRealEstateStats[] = []

  constructor(
    private readonly agentService: AgentService,
    private readonly handleError:HandleNotifyService
  ){}

  ngOnInit(): void {
    this.loadStats()
  }
  ngAfterViewInit(): void {
    if (this.scrollContainer?.nativeElement) {
      this.scrollContainer.nativeElement.addEventListener('scroll', this.nextPage.bind(this));
    }
  }

  loadStats() {
    const user = localStorage.getItem('user')
    if(user && !this.loading){
      this.loading= true
      this.agentService.estatesStats(user, this.page,this.limit).
        subscribe({
          next: (response:AgentDashboardRealEstateStats[]) =>{
            this.estatesStats = this.estatesStats.concat(response)
            this.loading = false
          },
          error: (err) => {
            this.handleError.showMessageError(err.error)
          }
        })
    }
  }
  
  nextPage(event: any) {
    console.log(event)
    const element = event.target;
    if (element.scrollHeight - element.scrollTop === element.clientHeight) {
      this.page++;
      this.loadStats();
    }
  }

  ngOnDestroy(): void {
    if (this.scrollContainer?.nativeElement) {
      this.scrollContainer.nativeElement.removeEventListener('scroll', this.nextPage.bind(this));
    }
  }
}

