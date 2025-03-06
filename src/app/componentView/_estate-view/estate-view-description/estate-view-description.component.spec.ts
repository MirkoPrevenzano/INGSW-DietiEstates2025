import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstateViewDescriptionComponent } from './estate-view-description.component';

describe('EstateViewDescriptionComponent', () => {
  let component: EstateViewDescriptionComponent;
  let fixture: ComponentFixture<EstateViewDescriptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstateViewDescriptionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstateViewDescriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
