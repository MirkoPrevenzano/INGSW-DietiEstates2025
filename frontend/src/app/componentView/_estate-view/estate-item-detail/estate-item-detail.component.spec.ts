import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstateItemDetailComponent } from './estate-item-detail.component';

describe('EstateItemComponent', () => {
  let component: EstateItemDetailComponent;
  let fixture: ComponentFixture<EstateItemDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstateItemDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstateItemDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
