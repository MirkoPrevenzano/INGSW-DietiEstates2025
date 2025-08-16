import { Coordinate } from "./coordinate";
import { RealEstatePreviewInfo } from "./response/support/realEstatePreviewInfo";

export interface CacheEstates {
    listEstatePreview: RealEstatePreviewInfo[]
    listCoordinate: Coordinate[]
    listRealEstateId: number[]
}