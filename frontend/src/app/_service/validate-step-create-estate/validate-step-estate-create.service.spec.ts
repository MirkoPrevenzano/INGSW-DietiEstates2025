import { TestBed } from '@angular/core/testing';

import { ValidateStepEstateCreateService } from './validate-step-estate-create.service';

describe('ValidateStepEstateCreateService', () => {
  let service: ValidateStepEstateCreateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ValidateStepEstateCreateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
