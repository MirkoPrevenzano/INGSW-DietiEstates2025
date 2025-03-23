import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonCustomComponent } from '../button-custom/button-custom.component';

@Component({
  selector: 'app-admin-home',
  imports: [
    CommonModule, 
    RouterModule,
    ButtonCustomComponent
  ],
  templateUrl: './admin-home.component.html',
  styleUrl: './admin-home.component.scss'
})
export class AdminHomeComponent {
  roleLevel=localStorage.getItem('role')
}