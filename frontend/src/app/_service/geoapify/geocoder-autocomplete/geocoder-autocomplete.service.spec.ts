import { TestBed } from '@angular/core/testing';
import { GeocoderAutocompleteService } from './geocoder-autocomplete.service';


describe('GeocoderAutocompleteServiceService', () => {
  let service: GeocoderAutocompleteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GeocoderAutocompleteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
