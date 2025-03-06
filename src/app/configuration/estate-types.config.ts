import { EstateType } from "../model/estateType";

export const ESTATE_TYPES_CONFIG: { [key in EstateType]: { label: string; type: string; formControlName: string; }[] } = {
    'For Sale': [
      { label: 'Notary deed state', type: 'string', formControlName: 'notaryDeedState' }
    ],
    'For Rent': [
      { label: 'Rent duration (Year)', type: 'number', formControlName: 'contractYears' },
      { label: 'Security deposit', type: 'number', formControlName: 'securityDeposit' }

    ]
    
    // Aggiungi altre configurazioni per nuovi tipi di annunci qui
  };