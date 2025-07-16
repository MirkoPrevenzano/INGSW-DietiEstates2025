import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddEstateComponent } from './add-estate/add-estate.component';
import {  Router, RouterLink } from '@angular/router';
import { ButtonCustomComponent } from '../../componentCustom/button-custom/button-custom.component';
import { RecentlyEstatePopupComponent } from './recently-estate-popup/recently-estate-popup.component';

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
export class AgentHomeComponent{
  typeEstate!:string
  constructor(private readonly router: Router) {}

  onSelectedType(typeEstate:string){
    this.router.navigate(
      ['/create-estate'], 
      { queryParams: { type: typeEstate } }
    );

  }


}
