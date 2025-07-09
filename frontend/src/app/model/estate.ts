import { Address } from "./address";
import { EstateDescribe } from "./estateDescribe";
import { EstateFeatures } from "./estateFeatures";
import { EstateLocationFeatures } from "./estateLocationFeatures";
import { EstateType } from "./estateType";

export interface Estate{
    addressDTO: Address,
    realEstateLocationFeaturesDTO: EstateLocationFeatures,
    realEstateMainFeaturesDTO: EstateDescribe,
    realEstateBooleanFeaturesDTO: EstateFeatures,
    type: EstateType
}