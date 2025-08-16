
package com.dietiEstates.backend.service.photo;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PhotoResult<T> 
{
    T photoValue;
    String contentType;
}