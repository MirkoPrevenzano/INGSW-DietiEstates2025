import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddEstateComponent } from '../add-estate/add-estate.component';
import { RecentlyEstatePopupComponent } from '../recently-estate-popup/recently-estate-popup.component';
import {  Router, RouterLink } from '@angular/router';
import { EstateCreateService } from '../../_service/rest-backend/estate-create/estate-create.service';
import { AgentService } from '../../_service/rest-backend/agent/agent.service';
import { ButtonCustomComponent } from '../button-custom/button-custom.component';

@Component({
  selector: 'app-agent-home',
  imports: [ 
    CommonModule, 
    AddEstateComponent, 
    RecentlyEstatePopupComponent,
    RouterLink,
    ButtonCustomComponent
  ],
  templateUrl: './agent-home.component.html',
  styleUrl: './agent-home.component.scss'
})
export class AgentHomeComponent implements OnInit{
  typeEstate!:string
  constructor(private readonly router: Router,
    private readonly estateService: EstateCreateService,
    private readonly agentService: AgentService
  ) {}

  ngOnInit(): void {
   const user= localStorage.getItem('user')
   if(user)
    this.agentService.recentyRealEstate(user).subscribe(response=>{
      //const uploadingDate = new Date(response[0].uploadingDate);
    
    })
  }

  
  onSelectedType(typeEstate:string){
    this.router.navigate(
      ['/create-estate'], 
      { queryParams: { type: typeEstate } }
    );

  }


}
