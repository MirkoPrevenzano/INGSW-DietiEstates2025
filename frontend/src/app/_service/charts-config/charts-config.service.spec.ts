import { TestBed } from '@angular/core/testing';

import { ChartsConfigService } from './charts-config.service';

describe('ChartsGenerateService', () => {
  let service: ChartsConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChartsConfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
