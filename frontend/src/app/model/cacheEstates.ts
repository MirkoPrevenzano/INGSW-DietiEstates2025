import { Coordinate } from "./coordinate";
import { EstatePreview } from "./estatePreview";

export interface CacheEstates {
    listEstatePreview: EstatePreview[]
    listCoordinate: Coordinate[]
    listRealEstateId: number[]
}