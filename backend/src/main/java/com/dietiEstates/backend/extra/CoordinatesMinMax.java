
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
    private Double minLatitude;

    @NonNull
    private Double maxLatitude;

    @NonNull
    private Double minLongitude;

    @NonNull
    private Double maxLongitude;
}