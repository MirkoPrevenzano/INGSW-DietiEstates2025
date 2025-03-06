import { TestBed } from '@angular/core/testing';

import { EstateFactoryService } from './estate-factory.service';

describe('EstateFactoryService', () => {
  let service: EstateFactoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EstateFactoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
