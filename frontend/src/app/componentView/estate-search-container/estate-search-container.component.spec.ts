import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstateSearchContainerComponent } from './estate-search-container.component';

describe('EstateSearchContainerComponent', () => {
  let component: EstateSearchContainerComponent;
  let fixture: ComponentFixture<EstateSearchContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstateSearchContainerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstateSearchContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
