import { TestBed } from '@angular/core/testing';

import { SavePersonalService } from './save-personalservice';

describe('SaveAgentService', () => {
  let service: SavePersonalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SavePersonalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
