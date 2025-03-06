import { TestBed } from '@angular/core/testing';

import { EstateService } from './estate.service';

describe('EstateService', () => {
  let service: EstateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EstateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
