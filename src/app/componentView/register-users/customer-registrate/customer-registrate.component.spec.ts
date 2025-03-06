import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerRegistrateComponent } from './customer-registrate.component';

describe('CustomerRegistrateComponent', () => {
  let component: CustomerRegistrateComponent;
  let fixture: ComponentFixture<CustomerRegistrateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustomerRegistrateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerRegistrateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
