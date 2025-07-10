
package com.dietiEstates.backend.resolver;


public interface Supportable<T>
{
    public boolean supports(Class<? extends T> clazz);
}