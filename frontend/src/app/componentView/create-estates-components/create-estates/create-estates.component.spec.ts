import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateEstatesComponent } from './create-estates.component';

describe('CreateEstatesComponent', () => {
  let component: CreateEstatesComponent;
  let fixture: ComponentFixture<CreateEstatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateEstatesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateEstatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
