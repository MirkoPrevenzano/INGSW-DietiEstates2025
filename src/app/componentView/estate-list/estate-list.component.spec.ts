import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstateListComponent } from './estate-list.component';

describe('EstateListComponent', () => {
  let component: EstateListComponent;
  let fixture: ComponentFixture<EstateListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstateListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstateListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
