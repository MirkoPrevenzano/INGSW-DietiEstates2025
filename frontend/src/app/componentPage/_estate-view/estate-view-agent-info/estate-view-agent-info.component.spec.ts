import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstateViewAgentInfoComponent } from './estate-view-agent-info.component';

describe('EstateViewAgentInfoComponent', () => {
  let component: EstateViewAgentInfoComponent;
  let fixture: ComponentFixture<EstateViewAgentInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstateViewAgentInfoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstateViewAgentInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
