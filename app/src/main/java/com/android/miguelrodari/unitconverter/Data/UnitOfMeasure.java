package com.android.miguelrodari.unitconverter.Data;

/**
 * Created by Miguel on 4/13/2015.
 */
public class UnitOfMeasure {

    private String Name;
    private String Category;
    private String StandardFormat;

    public UnitOfMeasure() {
        Name = null;
        Category = null;
        StandardFormat = null;
    }

    public UnitOfMeasure(String name, String category, String standardFormat) {
        Name = name;
        Category = category;
        StandardFormat = standardFormat;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getStandardFormat() {
        return StandardFormat;
    }

    public void setStandardFormat(String standardFormat) {
        StandardFormat = standardFormat;
    }

    @Override
    public String toString() {
        return Name;
    }

}
