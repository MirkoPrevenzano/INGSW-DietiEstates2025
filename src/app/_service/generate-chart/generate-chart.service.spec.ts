import { TestBed } from '@angular/core/testing';

import { GenerateChartService } from './generate-chart.service';

describe('GenerateChartService', () => {
  let service: GenerateChartService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GenerateChartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
