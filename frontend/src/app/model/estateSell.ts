import { Estate } from "./estate";

export interface EstateSell extends Estate{
    notaryDeedState: string,
    contractType: 'For Sale'
}