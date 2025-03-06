import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstateViewFeaturesComponent } from './estate-view-features.component';

describe('EstateViewFeaturesComponent', () => {
  let component: EstateViewFeaturesComponent;
  let fixture: ComponentFixture<EstateViewFeaturesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstateViewFeaturesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstateViewFeaturesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
