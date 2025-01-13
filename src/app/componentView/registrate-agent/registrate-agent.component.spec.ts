import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrateAgentComponent } from './registrate-agent.component';

describe('RegistrateAgentComponent', () => {
  let component: RegistrateAgentComponent;
  let fixture: ComponentFixture<RegistrateAgentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegistrateAgentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistrateAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
