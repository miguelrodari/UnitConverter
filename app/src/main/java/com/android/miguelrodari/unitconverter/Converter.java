package com.android.miguelrodari.unitconverter;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.measure.converter.UnitConverter;
import javax.measure.unit.Unit;

/**
 * Created by Miguel on 4/8/2015.
 */
public class Converter {
    private Converter() {
    }

    public static String getResult(String fromUnit, String toUnit, double value){
        double result = 0;
        try {
            Unit<? extends javax.measure.quantity.Quantity> uFromUnit = Unit.valueOf(fromUnit);
            Unit<? extends javax.measure.quantity.Quantity> uToUnit = Unit.valueOf(toUnit);
            if(uFromUnit.isCompatible(uToUnit)){
                UnitConverter unitConverter = uFromUnit.getConverterTo(uToUnit);
                result = unitConverter.convert(value);
            }
        }catch (IllegalArgumentException e){
            return "Symbol unknown - The character sequence cannot be correctly parsed";
        }
        return format(result);
    }

    public static String format(double value){
        NumberFormat format = new DecimalFormat("#.####");
        return format.format(value);
    }
}
