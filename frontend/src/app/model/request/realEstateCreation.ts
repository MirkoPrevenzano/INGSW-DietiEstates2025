import { Address } from "./support/address";
import { RealEstateMainFeatures } from "./support/realEstateMainFeatures";
import { RealEstateBooleanFeatures } from "./support/realEstateBooleanFeatures";
import { RealEstateLocationFeatures } from "./support/realEstateLocationFeatures";
import { EstateType } from "../estateType";

export interface RealEstateCreation{
    addressDto: Address,
    realEstateLocationFeaturesDto: RealEstateLocationFeatures,
    realEstateMainFeaturesDto: RealEstateMainFeatures,
    realEstateBooleanFeaturesDto: RealEstateBooleanFeatures,
    contractType: EstateType
}