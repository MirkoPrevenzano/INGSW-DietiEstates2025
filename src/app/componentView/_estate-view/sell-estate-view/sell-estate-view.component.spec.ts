import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellEstateViewComponent } from './sell-estate-view.component';

describe('SellEstateViewComponent', () => {
  let component: SellEstateViewComponent;
  let fixture: ComponentFixture<SellEstateViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SellEstateViewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SellEstateViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
