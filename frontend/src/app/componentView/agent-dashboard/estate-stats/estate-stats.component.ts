import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AgentService } from '../../../_service/rest-backend/agent/agent.service';
import { EstateStats } from '../../../model/estateStats';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-estate-stats',
  imports: [
    RouterLink,
    CommonModule
  ],
  templateUrl: './estate-stats.component.html',
  styleUrl: './estate-stats.component.scss'
})
export class EstateStatsComponent implements OnInit, AfterViewInit{
  page:number = 0
  limit: number = 10
  loading: boolean = false; //evito richieste multiple
  @ViewChild('scrollContainer') scrollContainer!: ElementRef;



  estatesStats: EstateStats[] = []

  constructor(
    private readonly agentService:AgentService
  ){}

  ngOnInit(): void {
    this.loadStats()
  }
  ngAfterViewInit(): void {
    this.scrollContainer.nativeElement.addEventListener('scroll', this.nextPage.bind(this));
  }

  loadStats() {
    const user = localStorage.getItem('user')
    if(user && !this.loading){
      this.loading= true
      this.agentService.estatesStats(user, this.page,this.limit).
        subscribe((response:EstateStats[])=>{
          this.estatesStats = this.estatesStats.concat(response)
          this.loading = false
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
}

