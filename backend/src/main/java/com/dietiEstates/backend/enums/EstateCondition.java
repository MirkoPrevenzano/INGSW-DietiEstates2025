
package com.dietiEstates.backend.enums;



public enum EstateCondition 
{
    UNDER_CONSTRUCTION("Under construction"), 
    NEW("new"), 
    TO_RENOVATE("To renovate"), 
    RENOVATED("Renovated"), 
    HABITABLE("Habitable"),
    GOOD("Good"), 
    EXCELLENT("Excellent");
    

    
    EstateCondition(String string) {}
}