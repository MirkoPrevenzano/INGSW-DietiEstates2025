
package com.dietiEstates.backend.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class VatNumberValidatorImpl implements ConstraintValidator<VatNumberValidator, CharSequence>
{

    @Override
    public boolean isValid(CharSequence vatNumber, ConstraintValidatorContext context) 
    {
        String vatNumberStr = vatNumber.toString();

        if (vatNumberStr.length() != 11) {
            return false;
        }
        
        if (!vatNumberStr.matches("\\d{11}")) {
            return false;
        }
        
        return validateByLuhnFormula(vatNumberStr);
    }


    private boolean validateByLuhnFormula(String vatNumber) 
    {
        int[] vatNumberDigits = new int[11];
        
        for (int i = 0; i < vatNumber.length(); i++) 
{            vatNumberDigits[i] = Character.getNumericValue(vatNumber.charAt(i));
             System.out.println("\ndigits " + i + ": " + vatNumberDigits[i]);
}        
        int oddDigitsSum = 0;  
        int evenDigitsSum = 0; 
        
        for (int i = 0; i < 11; i++) 
        {
            if (i % 2 == 0) {
                oddDigitsSum += vatNumberDigits[i];
            } else {
                int doubled = vatNumberDigits[i] * 2;
                evenDigitsSum += (doubled > 9) ? doubled - 9 : doubled;
            }
        }
        
        return (oddDigitsSum + evenDigitsSum) % 10 == 0;
    }
}