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
  hasMoreData: boolean = true; // Flag per verificare se ci sono ancora dati
  private scrollListener: ((event: any) => void) | null = null; // Riferimento al listener per rimuoverlo correttamente
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
      this.scrollListener = this.nextPage.bind(this);
      this.scrollContainer.nativeElement.addEventListener('scroll', this.scrollListener);
    }
  }

  loadStats() {
    const user = localStorage.getItem('user')
    if(user && !this.loading && this.hasMoreData){
      this.loading = true
      this.agentService.estatesStats(this.page,this.limit).
        subscribe({
          next: (response:AgentDashboardRealEstateStats[]) =>{
            if(response && response.length > 0) {
              this.estatesStats = this.estatesStats.concat(response)
              // Se la risposta ha meno elementi del limite, non ci sono più dati
              if(response.length < this.limit) {
                this.hasMoreData = false
              }
            } else {
              // Se la risposta è vuota, non ci sono più dati
              this.hasMoreData = false
            }
            this.loading = false
          },
          error: (err) => {
            this.handleError.showMessageError(err.error)
            this.loading = false // Assicurati che loading sia false anche in caso di errore
          }
        })
    }
  }
  
  nextPage(event: any) {
    const element = event.target;
    const threshold = 5; // Margine di 5px dal fondo
    
    // Verifica se siamo vicini al fondo del container
    const nearBottom = element.scrollTop + element.clientHeight >= element.scrollHeight - threshold;
    
    if (nearBottom && this.hasMoreData && !this.loading) {
      this.page++;
      this.loadStats();
    }
  }

  ngOnDestroy(): void {
    if (this.scrollContainer?.nativeElement && this.scrollListener) {
      this.scrollContainer.nativeElement.removeEventListener('scroll', this.scrollListener);
    }
  }

  // Metodo per resettare la paginazione (utile per refresh)
  resetPagination(): void {
    this.page = 0;
    this.hasMoreData = true;
    this.loading = false;
    this.estatesStats = [];
  }

  // Metodo per ricaricare i dati dall'inizio
  refreshStats(): void {
    this.resetPagination();
    this.loadStats();
  }
}

