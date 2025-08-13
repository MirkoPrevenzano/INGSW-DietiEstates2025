import { Address } from "./address";
import { EstateDescribe } from "./estateDescribe";
import { EstateFeatures } from "./estateFeatures";
import { EstateLocationFeatures } from "./estateLocationFeatures";
import { EstateType } from "./estateType";

export interface Estate{
    addressDto: Address,
    realEstateLocationFeaturesDto: EstateLocationFeatures,
    realEstateMainFeaturesDto: EstateDescribe,
    realEstateBooleanFeaturesDto: EstateFeatures,
    contractType: EstateType
}