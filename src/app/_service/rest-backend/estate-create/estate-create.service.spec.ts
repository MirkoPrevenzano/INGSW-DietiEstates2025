import { TestBed } from '@angular/core/testing';

import { EstateCreateService } from './estate-create.service';

describe('EstateCreateService', () => {
  let service: EstateCreateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EstateCreateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
