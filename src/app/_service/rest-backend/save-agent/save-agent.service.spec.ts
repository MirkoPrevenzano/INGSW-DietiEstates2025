import { TestBed } from '@angular/core/testing';

import { SaveAgentService } from './save-agent.service';

describe('SaveAgentService', () => {
  let service: SaveAgentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SaveAgentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
