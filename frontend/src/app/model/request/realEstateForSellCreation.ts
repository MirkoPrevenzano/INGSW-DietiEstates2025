import { RealEstateCreation } from "./realEstateCreation";

export interface RealEstateForSellCreation extends RealEstateCreation{
    notaryDeedState: string,
    contractType: 'For Sale'
}