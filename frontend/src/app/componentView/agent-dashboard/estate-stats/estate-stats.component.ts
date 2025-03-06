import { AfterViewInit, Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
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
    this.scrollContainer.nativeElement.addEventListener('scroll', this.onScroll.bind(this));
  }

  /*loadStats() {
    const user = localStorage.getItem('user');
    if (user && !this.loading) {
      this.loading = true;
      // Mock data for testing
      const estatesStats = [
        { id: 1, title: 'Estate 1', uploadDate: new Date(), offerNumber: 5, viewNumber: 100},
        { id: 2, title: 'Estate 2', uploadDate: new Date(), offerNumber: 3, viewNumber: 15 },
        { id: 3, title: 'Estate 3', uploadDate: new Date(), offerNumber: 8, viewNumber: 200 },
        { id: 4, title: 'Estate 4', uploadDate: new Date(), offerNumber: 2, viewNumber: 50 },
        { id: 5, title: 'Estate 5', uploadDate: new Date(), offerNumber: 6, viewNumber: 120 },
        { id: 6, title: 'Estate 6', uploadDate: new Date(), offerNumber: 4, viewNumber: 90 },
        { id: 7, title: 'Estate 7', uploadDate: new Date(), offerNumber: 7, viewNumber: 180 },
        { id: 8, title: 'Estate 8', uploadDate: new Date(), offerNumber: 1, viewNumber: 30 },
        { id: 1, title: 'Estate 1', uploadDate: new Date(), offerNumber: 5, viewNumber: 100 },
        { id: 2, title: 'Estate 2', uploadDate: new Date(), offerNumber: 3, viewNumber: 150 },
        { id: 3, title: 'Estate 3', uploadDate: new Date(), offerNumber: 8, viewNumber: 200 },
        { id: 4, title: 'Estate 4', uploadDate: new Date(), offerNumber: 2, viewNumber: 50 },
        { id: 5, title: 'Estate 5', uploadDate: new Date(), offerNumber: 6, viewNumber: 120 },
        { id: 6, title: 'Estate 6', uploadDate: new Date(), offerNumber: 4, viewNumber: 90 },
        { id: 7, title: 'Estate 7', uploadDate: new Date(), offerNumber: 7, viewNumber: 180 },
        { id: 8, title: 'Estate 8', uploadDate: new Date(), offerNumber: 1, viewNumber: 30 },
        { id: 1, title: 'Estate 1', uploadDate: new Date(), offerNumber: 5, viewNumber: 100 },
        { id: 2, title: 'Estate 2', uploadDate: new Date(), offerNumber: 3, viewNumber: 150 },
        { id: 3, title: 'Estate 3', uploadDate: new Date(), offerNumber: 8, viewNumber: 200 },
        { id: 4, title: 'Estate 4', uploadDate: new Date(), offerNumber: 2, viewNumber: 50 },
        { id: 5, title: 'Estate 5', uploadDate: new Date(), offerNumber: 6, viewNumber: 120 },
        { id: 6, title: 'Estate 6', uploadDate: new Date(), offerNumber: 4, viewNumber: 90 },
        { id: 7, title: 'Estate 7', uploadDate: new Date(), offerNumber: 7, viewNumber: 180 },
        { id: 8, title: 'Estate 8', uploadDate: new Date(), offerNumber: 1, viewNumber: 30 },
        { id: 1, title: 'Estate 1', uploadDate: new Date(), offerNumber: 5, viewNumber: 100 },
        { id: 2, title: 'Estate 2', uploadDate: new Date(), offerNumber: 3, viewNumber: 150 },
        { id: 3, title: 'Estate 3', uploadDate: new Date(), offerNumber: 8, viewNumber: 200 },
        { id: 4, title: 'Estate 4', uploadDate: new Date(), offerNumber: 2, viewNumber: 50 },
        { id: 5, title: 'Estate 5', uploadDate: new Date(), offerNumber: 6, viewNumber: 120 },
        { id: 6, title: 'Estate 6', uploadDate: new Date(), offerNumber: 4, viewNumber: 90 },
        { id: 7, title: 'Estate 7', uploadDate: new Date(), offerNumber: 7, viewNumber: 180 },
        { id: 8, title: 'Estate 8', uploadDate: new Date(), offerNumber: 1, viewNumber: 30 },
      ];
      const startIndex = (this.page - 1) * this.limit;
      const endIndex = this.page * this.limit;
      const response = estatesStats.slice(startIndex, endIndex);
      this.estatesStats = this.estatesStats.concat(response);
      this.loading = false;
    }
  }*/

  loadStats() {
    const user = localStorage.getItem('user')
    if(user && !this.loading){
      this.loading= true
      this.agentService.estatesStats(user, this.page,this.limit).
        subscribe((response:EstateStats[])=>{
          console.log(response)
          this.estatesStats = this.estatesStats.concat(response)
          this.loading = false
        })
    }
  }
  
  onScroll(event: any) {
    console.log(event)
    const element = event.target;
    if (element.scrollHeight - element.scrollTop === element.clientHeight) {
      this.page++;
      this.loadStats();
    }
  }
}

