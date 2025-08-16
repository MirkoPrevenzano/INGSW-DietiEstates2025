import { RealEstateCreation } from "./realEstateCreation";

export interface RealEstateForRentCreation extends RealEstateCreation{
    contractYears: number,
    securityDeposit: number,
    contractType: 'For Rent'
}