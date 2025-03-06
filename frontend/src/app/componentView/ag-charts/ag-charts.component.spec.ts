import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AgChartsComponent } from './ag-charts.component';


describe('AgChartsComponent', () => {
  let component: AgChartsComponent;
  let fixture: ComponentFixture<AgChartsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgChartsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgChartsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
