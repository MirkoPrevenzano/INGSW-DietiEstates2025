export interface Address{
    houseNumber: number,
    street: string,
    state: string,
    postalCode: string,
    city: string,
    country: string,
    longitude?: number,
    latitude?: number
}