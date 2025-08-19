import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-button-custom',
  imports:[
    CommonModule,
    RouterModule
  ],
  template: `
    <!-- Pulsante con icona -->
    <button
      *ngIf="icon"
      [ngClass]="buttonClass"
      [routerLink]="routerLink"
      (click)="handleClick($event)"
      [ngStyle]="width ? {'width.px': width} : {}"
      class="px-8 py-4 text-back font-bold rounded-xl shadow-lg transition-all duration-300 transform hover:scale-105 hover:shadow-xl sm:w-full w-full flex items-center justify-start gap-4 relative overflow-hidden"
    >
      <div class="text-2xl bg-white bg-opacity-20 p-2 rounded-lg backdrop-blur-sm">
        {{ icon }}
      </div>
      <span class="text-lg font-semibold">{{ text }}</span>
      <div class="absolute inset-0 bg-gradient-to-r from-transparent to-white opacity-0 hover:opacity-10 transition-opacity duration-300"></div>
    </button>

    <!-- Pulsante senza icona -->
    <button
      *ngIf="!icon"
      [ngClass]="buttonClass"
      [routerLink]="routerLink"
      (click)="handleClick($event)"
      [ngStyle]="width ? {'width.px': width} : {}"
      class="px-6 py-3 text-back font-semibold rounded-xl shadow-lg transition transform hover:scale-105 sm:w-full w-full flex items-center justify-center"
    >
      <span>{{ text }}</span>
    </button>
  `,
  styles: []
})
export class ButtonCustomComponent {
  @Input() text: string = '';
  @Input() width: number |null = null
  @Input() color: string = 'bg-primary'; 
  @Input() hoverColor: string = 'hover:bg-secondary';
  @Input() routerLink: string | null = null;
  @Input() icon: string | null = null;
  @Output() clickEvent = new EventEmitter<Event>();

  get buttonClass() {
    return `${this.color} hover:${this.hoverColor}`;
  }

  handleClick(event: Event) {
    if (!this.routerLink) {
      this.clickEvent.emit(event);
    }
  }
}