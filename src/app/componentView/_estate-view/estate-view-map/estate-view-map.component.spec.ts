import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstateViewMapComponent } from './estate-view-map.component';

describe('EstateViewMapComponent', () => {
  let component: EstateViewMapComponent;
  let fixture: ComponentFixture<EstateViewMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstateViewMapComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstateViewMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
