import { TestBed } from '@angular/core/testing';

import { HandleNotifyService } from './handle-notify.service';

describe('HandleNotifyService', () => {
  let service: HandleNotifyService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HandleNotifyService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
