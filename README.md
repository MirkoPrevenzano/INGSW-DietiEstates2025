# INGSW-DietiEstates2025


    /*
    *PasswordRequest ha tre parametri, vecchia e nuova password e username dell'admin. Nel backend si deve controllare se il token
    *di autenticazione passato nella request è valido, bisogna controllare se l'user ricavato dal token, corrisponde all'user presente
    *nel body. Bisogna controllare se la vecchia password corrisponde a quella reale. Bisogna testare se la password è sicura (doppio controllo,
    *tale controllo è fatto in primis nel frontend).
    *Se una di tali condizioni non è valida bisogna inviare nella response il messaggio preciso di errore associato al codice corretto
    * 
    */


Inserimento di un'appartamento in vendita:
url: /agent/{username}/create-sell-estate
cosa passo:
-estate: Estate,
-notaryDeedState: string,
-sellingPrice:number

Inserimento di un'appartamento in affitto:
url: /agent/{username}/create-rent-estate
cosa passo:
-estate: Estate,
-contractYears: number,
-rentalPrice: number,
-securityDeposit: number


Oggetto Estate:
-address: Address,
-locationFeatures: EstateLocationFeatures,
-info: EstateDescribe,
-features: EstateFeatures

Oggetto Address:
-housenumber: number,
-street: string,
-state: string,
-postcode: string,
-city: string,
-country: string,
-lon: string,
-lat: string

Oggetto EstateLocationFeatures:
-isNearPark: boolean,
-isNearPublicTransport: boolean,
-isNearSchool: boolean

Oggetto EstateDescribe:
-description: string,
-size: number,
-roomsNumber: number,
-floorNumber: number,
-energyClass: string,
-parkingSpacesNumber: number,
-condoFee: number,
-furnitureState: string,
-houseState: string,

Oggetto EstateFeatures:
-hasHeating: boolean,
-hasConcierge: boolean,
-hasAirConditioning: boolean,
-hasTerrace: boolean,
-hasGarage: boolean,
-hasBalcony: boolean,
-hasGarden: boolean,
-hasSwimmingPool: boolean

