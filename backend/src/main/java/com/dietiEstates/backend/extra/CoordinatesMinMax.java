
package com.dietiEstates.backend.extra;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CoordinatesMinMax 
{
    @NonNull
    private Double latMin;

    @NonNull
    private Double latMax;

    @NonNull
    private Double longMin;

    @NonNull
    private Double longMax;
}