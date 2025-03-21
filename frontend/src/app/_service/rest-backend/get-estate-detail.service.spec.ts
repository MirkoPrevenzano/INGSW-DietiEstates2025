import { TestBed } from '@angular/core/testing';

import { GetEstateDetailService } from './get-estate-detail.service';

describe('GetEstateDetailService', () => {
  let service: GetEstateDetailService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GetEstateDetailService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
