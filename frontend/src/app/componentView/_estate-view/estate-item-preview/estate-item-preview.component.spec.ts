import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstateItemPreviewComponent } from './estate-item-preview.component';

describe('EstateItemPreviewComponent', () => {
  let component: EstateItemPreviewComponent;
  let fixture: ComponentFixture<EstateItemPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstateItemPreviewComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstateItemPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
