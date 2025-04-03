import { TestBed } from '@angular/core/testing';

import { RedirectHomeService } from './redirect-home.service';

describe('RedirectHomeService', () => {
  let service: RedirectHomeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RedirectHomeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
