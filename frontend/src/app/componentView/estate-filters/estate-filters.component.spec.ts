import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstateFiltersComponent } from './estate-filters.component';

describe('EstateFiltersComponent', () => {
  let component: EstateFiltersComponent;
  let fixture: ComponentFixture<EstateFiltersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstateFiltersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstateFiltersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
