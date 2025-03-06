import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstateStatsComponent } from './estate-stats.component';

describe('EstateStatsComponent', () => {
  let component: EstateStatsComponent;
  let fixture: ComponentFixture<EstateStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstateStatsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstateStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
