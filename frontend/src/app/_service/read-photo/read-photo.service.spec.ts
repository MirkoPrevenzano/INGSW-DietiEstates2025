import { TestBed } from '@angular/core/testing';

import { ReadPhotoService } from './read-photo.service';

describe('ReadPhotoService', () => {
  let service: ReadPhotoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReadPhotoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
