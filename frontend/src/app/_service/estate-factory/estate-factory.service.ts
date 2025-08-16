import { Injectable } from '@angular/core';
import { RealEstateForRentCreation } from '../../model/request/realEstateForRentCreation';
import { RealEstateForSellCreation } from '../../model/request/realEstateForSellCreation';
import { RealEstateCreation } from '../../model/request/realEstateCreation';

@Injectable({
  providedIn: 'root'
})
export class EstateFactoryService {

  createEstate(estate: RealEstateCreation, additionalFields: any): RealEstateCreation {
    switch (estate.contractType) {
      case 'For Sale': {
        return this.createEstateSell(estate, additionalFields);
      }
      case 'For Rent': {
        return this.createEstateRent(estate, additionalFields);
      }
      default: throw new Error('Invalid estate type')
    }
  }

  private createEstateSell(estate: any, additionalFields: any): RealEstateForSellCreation {
    return {
      ...estate,
      notaryDeedState: additionalFields.notaryDeedState,
      sellingPrice: additionalFields.sellingPrice
    };
  }

  private createEstateRent(estate: any, additionalFields: any): RealEstateForRentCreation {
    return {
      ...estate,
      contractYears: additionalFields.contractYears,
      rentalPrice: additionalFields.rentalPrice,
      securityDeposit: additionalFields.securityDeposit

    };
  }
}