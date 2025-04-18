

  COSE DA FARE CON IL FRONTEND
  - nella visualizzazione dell'estate indicare lo stato di vendita e le info delle agent
  - L'agente deve poter accedere al dettaglio dei propri estate
  -vedere responsive






 




















/*****************************************************************************************************************************************************/

lista  estate più recenti di un certo agente (dato l'username e un valori limit che indica il numero di estate da restituire). Voglio che mi restituisca descrizione, titolo, data e id

Dato determinati filtri, numero di pagina e limit che indica il numero di estate da restituire passami le foto, descrizione, data, titolo, id e città (da considerare se conviene inviare tutte le info dei 10 immobili o meglio inviare solo certe info)

dato un certo id resituisci tutte le info di tale estate

 
metti google
cambiare validatorEmail

Per rendere la ricerca più efficiente è possibile attraverso la tecnica della paginazione (lazy loading)

 Caricare in modo asincrono le info, prima titolo e descrizione. e poi le foto. Vedere come
 Usare asincronismo dal server per ottenere le info degli estate
 Per quanto riguarda estate, mettere price generael
(piccola chicca, vietare di far inserire una password uguale a quella precedente




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

=======
# DietiEstatesFrontend

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 18.0.3.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
>>>>>>> origin/master
