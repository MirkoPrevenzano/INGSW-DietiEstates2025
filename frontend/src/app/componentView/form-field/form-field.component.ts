import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-form-field',
  templateUrl: './form-field.component.html',
  styleUrls: ['./form-field.component.scss'],
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => FormFieldComponent),
      multi: true
    }
  ]
})
export class FormFieldComponent implements ControlValueAccessor {

  @Input() label!: string;
  @Input() type: string = 'text';
  @Input() controlName!: string;
  @Input() options: string[] = []; // Per gestire le opzioni del select
  @Input() rows: number = 1; // Per gestire le righe del textarea
  @Input() form!: FormGroup
  @Input() minlength: number | null = null;
  @Input() maxlength: number | null = null;
  @Input() minNumber: number  = 0;
  @Input() maxNumber: number | null = null;
  private _value: any = '';

  get value(): any {
    return this._value;
  }

  set value(val: any) {
    this._value = val;
    this.onChange(val);
    this.onTouched();
  }

  onChange: any = () => {};
  onTouched: any = () => {};

  writeValue(value: any): void {
    this._value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    // Implementa la logica per disabilitare il campo se necessario
  }

  updateValue(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.value = input.value;
  }
}