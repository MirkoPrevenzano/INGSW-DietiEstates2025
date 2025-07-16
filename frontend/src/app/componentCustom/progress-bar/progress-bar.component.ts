import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-progress-bar',
  imports:[CommonModule],
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.scss']
})
export class ProgressBarComponent {
  @Input() currentStep = 0;
  @Input() steps: string[] = []

  getProgressWidth() {
    if ((this.steps.length-1) === 0) {
      return '0%';
    }
    return (this.currentStep / (this.steps.length-1)) * 100 + '%';
  }
  
}