import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ControlValueAccessor, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatIcon } from '@angular/material/icon';

@Component({
  selector: 'app-checkbox',
  imports:[
    ReactiveFormsModule, 
    MatIcon,
    CommonModule
  ],
  templateUrl: './checkbox.component.html',
  styleUrls: ['./checkbox.component.scss']
})
export class CheckboxComponent implements ControlValueAccessor {
  @Input() label!: string;
  @Input() controlName!: string;
  @Input() form!: FormGroup;
  @Input() icon?: string;

  get value(): boolean {
    const control = this.form?.controls?.[this.controlName];
    return control?.value || false;
  }

  set value(val: boolean) {
    const control = this.form?.controls?.[this.controlName];
    if (control) {
      control.setValue(val);
      this.onChange(val);
      this.onTouched();
    }
  }

  onChange: any = () => {};
  onTouched: any = () => {};

  writeValue(value: boolean): void {
    if (this.form?.controls?.[this.controlName]) {
      this.form.controls[this.controlName].setValue(value, { emitEvent: false });
    }
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }
  
  setValue(val: boolean): void {
    if (this.form?.controls?.[this.controlName]) {
      this.value = val;
    }
  }
}