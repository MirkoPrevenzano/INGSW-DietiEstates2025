import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-password-field',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './password-field.component.html',
  styleUrl: './password-field.component.scss'
})
export class PasswordFieldComponent {
  @Input() form!:FormGroup
  @Input() controlName!: string
  @Input() label!: string

  showPassword:boolean = false

  togglePasswordVisibility(){
    this.showPassword = !this.showPassword
  }
}
