import { Injectable } from '@angular/core';
import { EstateRent } from '../../model/estateRent';
import { EstateSell } from '../../model/estateSell';

@Injectable({
  providedIn: 'root'
})
export class EstateFactoryService {

  createEstate(estate: any, additionalFields: any) {
    switch (estate.type) {
      case 'For Sale': {
        return this.createEstateSell(estate, additionalFields);
      }
      case 'For Rent': {
        return this.createEstateRent(estate, additionalFields);
      }
      default: throw new Error('Invalid estate type')
    }
  }

  private createEstateSell(estate: any, additionalFields: any): EstateSell {
    return {
      ...estate,
      notaryDeedState: additionalFields.notaryDeedState,
      sellingPrice: additionalFields.sellingPrice
    };
  }

  private createEstateRent(estate: any, additionalFields: any): EstateRent {
    return {
      ...estate,
      contractYears: additionalFields.contractYears,
      rentalPrice: additionalFields.rentalPrice,
      securityDeposit: additionalFields.securityDeposit

    };
  }
}