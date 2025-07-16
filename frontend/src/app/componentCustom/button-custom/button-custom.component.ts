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
    <button
      [ngClass]="buttonClass"
      [routerLink]="routerLink"
      (click)="handleClick($event)"
      [ngStyle]="width ? {'width.px': width} : {}"
      class=" px-6 py-3 text-back font-semibold rounded-xl shadow-lg transition transform hover:scale-105 sm:w-full w-full"
    >
      {{ text }}
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