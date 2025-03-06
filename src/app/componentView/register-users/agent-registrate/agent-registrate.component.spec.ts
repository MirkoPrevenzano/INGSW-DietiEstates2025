import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgentRegistrateComponent } from './agent-registrate.component';

describe('AgentRegistrateComponent', () => {
  let component: AgentRegistrateComponent;
  let fixture: ComponentFixture<AgentRegistrateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgentRegistrateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgentRegistrateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
