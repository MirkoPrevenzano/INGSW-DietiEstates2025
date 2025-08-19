
package com.dietiestates.backend.enums;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum MonthLabel 
{
    JANUARY("JAN", 0),
    FEBRUARY("FEB", 1),
    MARCH("MAR", 2),
    APRIL("APR", 3),
    MAY("MAY", 4),
    JUNE("JUN", 5),
    JULY("JUL", 6),
    AUGUST("AUG", 7),
    SEPTEMBER("SEP", 8),
    OCTOBER("OCT", 9),
    NOVEMBER("NOV", 10),
    DECEMBER("DEC", 11);


    private final String monthAbbreviation;
    private final int index;


    public static MonthLabel[] getMonthLabels() 
    {
        return values();
    }

    public static String[] getMonthAbbreviations() 
    {
        return Arrays.stream(values())
                     .map(MonthLabel::getMonthAbbreviation)
                     .toArray(String[]::new);
    }
}
