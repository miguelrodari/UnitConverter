package com.android.miguelrodari.unitconverter;

/**
 * Created by Miguel on 4/9/2015.
 */
public class Constants {

    public static final String UNITS_OF_MEASURE_XML = "units_of_measure.xml";

    private static final String Digits     = "(\\p{Digit}+)";
    private static final String HexDigits  = "(\\p{XDigit}+)";
    private static final String Exp        = "[eE][+-]?"+Digits;
    public static final String DOUBLE_REGEX    =
            ("[\\x00-\\x20]*"+
                    "[+-]?(" +
                    "NaN|" +
                    "Infinity|" +
                    "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+
                    "(\\.("+Digits+")("+Exp+")?)|"+
                    "((" +
                    "(0[xX]" + HexDigits + "(\\.)?)|" +
                    "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +
                    ")[pP][+-]?" + Digits + "))" +
                    "[fFdD]?))" +
                    "[\\x00-\\x20]*");
}
