import { TestBed } from '@angular/core/testing';

import { RegisterValidationService } from './register-validation.service';

describe('RegisterValidationService', () => {
  let service: RegisterValidationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegisterValidationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
