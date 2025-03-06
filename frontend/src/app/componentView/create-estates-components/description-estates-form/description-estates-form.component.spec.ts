import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DescriptionEstatesFormComponent } from './description-estates-form.component';

describe('DescriptionEstatesFormComponent', () => {
  let component: DescriptionEstatesFormComponent;
  let fixture: ComponentFixture<DescriptionEstatesFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DescriptionEstatesFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DescriptionEstatesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
