package com.android.miguelrodari.unitconverter.presenters;

import android.widget.ArrayAdapter;

import com.android.miguelrodari.unitconverter.fragments.MainFragment;
import com.android.miguelrodari.unitconverter.models.UnitConverterUtils;
import com.android.miguelrodari.unitconverter.models.UnitConverterWrapper;
import com.android.miguelrodari.unitconverter.models.UnitOfMeasure;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Miguel on 5/19/2015.
 */
public class UnitConverterPresenter {
    private List<UnitOfMeasure> unitList;
    private MainFragment fragment;

    public UnitConverterPresenter( MainFragment fragment){
        this.fragment = fragment;
        loadUnitsOfMeasure();
    }

    public void loadUnitsOfMeasure(){
        unitList = UnitConverterUtils.loadXML(fragment.getActivity());
    }

    public String getConversionResult(Object fromSelectedItem, Object toSelectedItem, String value){
        if (!Pattern.matches(UnitConverterUtils.DOUBLE_REGEX, value)){
            return "";
        }
        UnitOfMeasure fromUnit = (UnitOfMeasure) fromSelectedItem;
        UnitOfMeasure toUnit = (UnitOfMeasure) toSelectedItem;
        return UnitConverterWrapper.getResult(fromUnit.getStandardFormat(), toUnit.getStandardFormat(), Double.valueOf(value));
    }

    public void loadUnitArray(final String category){

        List<UnitOfMeasure> filteredList = Lists.newArrayList(Collections2.filter(unitList, new Predicate<UnitOfMeasure>() {
            @Override
            public boolean apply(UnitOfMeasure unitOfMeasure) {
                return unitOfMeasure.getCategory().contains(category);
            }
        }));
        ArrayAdapter adapter = new ArrayAdapter<>(fragment.getActivity(),android.R.layout.simple_spinner_item, filteredList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragment.setSpinnerAdapter(adapter);
    }
}
