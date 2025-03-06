import { TestBed } from '@angular/core/testing';

import { PasswordChangeControlService } from './password-change-control.service';

describe('PasswordChangeControlService', () => {
  let service: PasswordChangeControlService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PasswordChangeControlService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
