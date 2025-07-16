import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecentlyEstatePopupComponent } from './recently-estate-popup.component';

describe('RecentlyEstatePopupComponent', () => {
  let component: RecentlyEstatePopupComponent;
  let fixture: ComponentFixture<RecentlyEstatePopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecentlyEstatePopupComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecentlyEstatePopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
