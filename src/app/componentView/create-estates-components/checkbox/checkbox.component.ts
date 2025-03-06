import { Component, Input } from '@angular/core';
import { ControlValueAccessor, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-checkbox',
  imports:[ReactiveFormsModule],
  templateUrl: './checkbox.component.html',
  styleUrls: ['./checkbox.component.scss']
})
export class CheckboxComponent implements ControlValueAccessor {
  @Input() label!: string;
  @Input() controlName!: string;
  @Input() form!: FormGroup;

  get value(): boolean {
    return this.form.controls[this.controlName].value;
  }

  set value(val: boolean) {
    this.form.controls[this.controlName].setValue(val);
    this.onChange(val);
    this.onTouched();
  }

  onChange: any = () => {};
  onTouched: any = () => {};

  writeValue(value: boolean): void {
    this.value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.form.controls[this.controlName].disable();
    } else {
      this.form.controls[this.controlName].enable();
    }
  }

  setValue(val: boolean): void {
    this.value = val;
  }
}