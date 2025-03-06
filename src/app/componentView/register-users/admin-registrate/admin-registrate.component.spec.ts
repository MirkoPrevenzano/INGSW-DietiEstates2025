import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminRegistrateComponent } from './admin-registrate.component';

describe('AdminRegistrateComponent', () => {
  let component: AdminRegistrateComponent;
  let fixture: ComponentFixture<AdminRegistrateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminRegistrateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminRegistrateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
