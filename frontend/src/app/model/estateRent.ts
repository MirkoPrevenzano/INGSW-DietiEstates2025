import { Estate } from "./estate";

export interface EstateRent extends Estate{
    contractYears: number,
    securityDeposit: number,
    type: 'For Rent'
}