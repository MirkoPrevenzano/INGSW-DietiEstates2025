import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ButtonCustomComponent } from '../button-custom/button-custom.component';

@Component({
  selector: 'app-home',
  imports: [
    RouterModule,
    ButtonCustomComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {}
