import { TestBed } from '@angular/core/testing';

import { CreateStaffService } from './create-staff.service';

describe('SaveAgentService', () => {
  let service: CreateStaffService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CreateStaffService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
