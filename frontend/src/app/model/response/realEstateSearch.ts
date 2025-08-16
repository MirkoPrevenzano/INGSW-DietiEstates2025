import { RealEstatePreviewInfo } from "./support/realEstatePreviewInfo";

export interface RealEstateSearch{
    realEstatePreviewInfoDtoList: RealEstatePreviewInfo[],
    totalElementsOfPage:number,
    totalElements:number,
    totalPages:number
}