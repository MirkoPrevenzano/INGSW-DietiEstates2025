import { TestBed } from '@angular/core/testing';

import { AddressVerificationService } from './address-verification.service';

describe('AddressVerificationService', () => {
  let service: AddressVerificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddressVerificationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
