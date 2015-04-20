package com.android.miguelrodari.unitconverter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.miguelrodari.unitconverter.Data.UnitOfMeasure;
import com.android.miguelrodari.unitconverter.Data.XMLParser;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Miguel on 4/7/2015.
 */
public class MainFragment extends Fragment {

    private Spinner categorySpinner;
    private Spinner unitSpinner1;
    private Spinner unitSpinner2;
    private TextView inputTextView1;
    private TextView inputTextView2;
    private List<UnitOfMeasure> unitList;
    private int selectedUnitSpinner1 = -1;
    private int selectedUnitSpinner2 = -1;
    private static final String STATE_UNITSPINNER1 = "SelectedUnitSpinner1";
    private static final String STATE_UNITSPINNER2 = "SelectedUnitSpinner2";
    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        unitList = XMLParser.loadXML(getActivity());
        categorySpinner = (Spinner) rootView.findViewById(R.id.spinnerCategory);
        unitSpinner1 = (Spinner) rootView.findViewById(R.id.spinnerUnit1);
        unitSpinner2 = (Spinner) rootView.findViewById(R.id.spinnerUnit2);
        inputTextView1 = (TextView) rootView.findViewById(R.id.inputText1);
        inputTextView2 = (TextView) rootView.findViewById(R.id.inputText2);

        categorySpinner.setOnItemSelectedListener( new SpinnerOnItemSelectedListener());
        unitSpinner1.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
        unitSpinner2.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
        inputTextView1.addTextChangedListener(new TWatcher(inputTextView1));
        inputTextView2.addTextChangedListener(new TWatcher(inputTextView2));

        if(savedInstanceState != null){
            selectedUnitSpinner1 = savedInstanceState.getInt(STATE_UNITSPINNER1);
            selectedUnitSpinner2 = savedInstanceState.getInt(STATE_UNITSPINNER2);
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_UNITSPINNER1, unitSpinner1.getSelectedItemPosition());
        outState.putInt(STATE_UNITSPINNER2, unitSpinner2.getSelectedItemPosition());
        super.onSaveInstanceState(outState);
    }

    private void loadUnitArrays(final int selectedItemId) {

        //The onItemSelected() can be triggered more than once when initializing,
        //this validation is to avoid reloading when it's not needed.
        if (unitSpinner1.getSelectedItem() != null &&
                ((UnitOfMeasure) unitSpinner1.getSelectedItem()).getCategory().equals(categorySpinner.getItemAtPosition(selectedItemId))){
            return;
        }

        List<UnitOfMeasure> filteredList = Lists.newArrayList(Collections2.filter(unitList, new Predicate<UnitOfMeasure>() {
            @Override
            public boolean apply(UnitOfMeasure unitOfMeasure) {
                return unitOfMeasure.getCategory().contains(categorySpinner.getItemAtPosition(selectedItemId).toString());
            }
        }));
        ArrayAdapter adapter = new ArrayAdapter<UnitOfMeasure>(getActivity(),android.R.layout.simple_spinner_item, filteredList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        unitSpinner1.setAdapter(adapter);
        unitSpinner2.setAdapter(adapter);

        if (selectedUnitSpinner1 >= 0 && unitSpinner1.getCount() >= selectedUnitSpinner1){
            unitSpinner1.setSelection(selectedUnitSpinner1);
            selectedUnitSpinner1 = -1;
        }
        if (selectedUnitSpinner2 >= 0 && unitSpinner2.getCount() >= selectedUnitSpinner2){
            unitSpinner2.setSelection(selectedUnitSpinner2);
            selectedUnitSpinner2 = -1;
        }

    }

    private String getResult(String fromUnit, String toUnit, String value){
        if (!Pattern.matches(Constants.DOUBLE_REGEX, value)){
            return "";
        }
        return Converter.getResult(fromUnit, toUnit, Double.valueOf(value));
    }

    private class TWatcher implements TextWatcher{
        private View view;

        private TWatcher(View view){
            this.view = view;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(view.isFocused()){
                String selectedUnit1 = ((UnitOfMeasure) unitSpinner1.getSelectedItem()).getStandardFormat();
                String selectedUnit2 = ((UnitOfMeasure) unitSpinner2.getSelectedItem()).getStandardFormat();
                if(view.getId() == inputTextView1.getId()){
                    String result = getResult(
                            selectedUnit1,
                            selectedUnit2,
                            s.toString());
                    inputTextView2.setText(result);
                }else{
                    String result = getResult(
                            selectedUnit2,
                            selectedUnit1,
                            s.toString());
                    inputTextView1.setText(result);
                }
            }
        }
    }

    private class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getId() == R.id.spinnerCategory){
                loadUnitArrays(parent.getSelectedItemPosition());
            }else if(parent.getId() == R.id.spinnerUnit1 || parent.getId() == R.id.spinnerUnit2){
                String selectedUnit1 = ((UnitOfMeasure) unitSpinner1.getSelectedItem()).getStandardFormat();
                String selectedUnit2 = ((UnitOfMeasure) unitSpinner2.getSelectedItem()).getStandardFormat();
                String inputValue = inputTextView1.getText().toString();
                inputTextView2.setText(getResult(
                        selectedUnit1,
                        selectedUnit2,
                        inputValue));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}