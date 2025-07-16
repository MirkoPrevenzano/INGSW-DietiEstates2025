import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddressEstatesComponent } from './address-estates.component';

describe('AddressEstatesComponent', () => {
  let component: AddressEstatesComponent;
  let fixture: ComponentFixture<AddressEstatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddressEstatesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddressEstatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
