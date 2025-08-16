export interface ErrorResponse{
    detail:string,
    path:string,
    status:number,
    subErrors:string[],
    timestamp:Date,
    title:string,
    type:string
}

