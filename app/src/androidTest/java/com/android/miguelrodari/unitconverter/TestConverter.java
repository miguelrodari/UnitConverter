package com.android.miguelrodari.unitconverter;

import android.test.AndroidTestCase;

/**
 * Created by Miguel on 4/20/2015.
 */
public class TestConverter extends AndroidTestCase {
    public void testConverter(){

        assertEquals("Error: the result from Celsius to Fahrenheit is incorrect",
                Converter.getResult("℃", "°F", 24),
                "75.2");

        assertEquals("Error: the result from Yard to Meter is incorrect",
                Converter.getResult("yd", "m", 1),
                "0.9144");

        assertEquals("Error: the result from Hectare to Square meter is incorrect",
                Converter.getResult("ha", "m²", 20),
                "200000");

        assertEquals("Error: the result from Cubic Inch to Ounce Liquid UK is incorrect",
                Converter.getResult("in³", "oz_uk", 23.5),
                "13.5535");

        assertEquals("Error: the result from Ton UK to Gram is incorrect",
                Converter.getResult("ton_uk", "g", 13.7),
                "13919842.6506");

        assertEquals("Error: the result from Pound to bit is incorrect",
                Converter.getResult("lb", "bit", 78),
                "0");

        assertEquals("Error: the result from converting invalid units is incorrect",
                Converter.getResult("this is an invalid unit", "bit", 1),
                "Symbol unknown - The character sequence cannot be correctly parsed");
    }

}
