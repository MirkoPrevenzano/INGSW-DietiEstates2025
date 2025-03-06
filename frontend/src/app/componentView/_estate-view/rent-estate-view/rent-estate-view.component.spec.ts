import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentEstateViewComponent } from './rent-estate-view.component';

describe('RentEstateViewComponent', () => {
  let component: RentEstateViewComponent;
  let fixture: ComponentFixture<RentEstateViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RentEstateViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentEstateViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
