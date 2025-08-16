import { RealEstateCreation } from "../request/realEstateCreation";
import { AgentPublicInfo } from "./support/agentPublicInfo";

export interface RealEstateCompleteInfo{
    realEstateCreationDto:RealEstateCreation,
    agentPublicInfoDto:AgentPublicInfo
}