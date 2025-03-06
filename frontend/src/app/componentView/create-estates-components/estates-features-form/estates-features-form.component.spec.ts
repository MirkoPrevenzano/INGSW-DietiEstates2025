import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstatesFeaturesFormComponent } from './estates-features-form.component';

describe('EstatesFeaturesFormComponent', () => {
  let component: EstatesFeaturesFormComponent;
  let fixture: ComponentFixture<EstatesFeaturesFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstatesFeaturesFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstatesFeaturesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
