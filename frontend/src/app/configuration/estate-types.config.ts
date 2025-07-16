import { EstateType } from "../model/estateType";

export const ESTATE_TYPES_CONFIG: { [key in EstateType]: { label: string; type: string; formControlName: string; options?: string[]; }[] } = {
    'For Sale': [
      { label: 'Notary deed state', 
        type: 'select', 
        formControlName: 'notaryDeedState',
        options: [
          "Free",
          "Occupied",
          "Bare property"
        ] 
      }
    ],
    'For Rent': [
      { label: 'Rent duration (Year)', 
        type: 'number', 
        formControlName: 'contractYears' 
      },
      { label: 'Security deposit', 
        type: 'number', 
        formControlName: 'securityDeposit' 
      }

    ]
    
  };