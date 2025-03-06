import { TestBed } from '@angular/core/testing';

import { EstateDataService } from './estate-data.service';

describe('EstateDataService', () => {
  let service: EstateDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EstateDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
