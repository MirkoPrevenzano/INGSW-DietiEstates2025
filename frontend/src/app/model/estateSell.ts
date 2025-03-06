import { Estate } from "./estate";

export interface EstateSell extends Estate{
    notaryDeedState: string,
    type: 'For Sale'
}