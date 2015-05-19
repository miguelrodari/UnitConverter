package com.android.miguelrodari.unitconverter.fragments;

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

import com.android.miguelrodari.unitconverter.R;
import com.android.miguelrodari.unitconverter.presenters.UnitConverterPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Miguel on 4/7/2015.
 */
public class MainFragment extends Fragment {

    @InjectView(R.id.spinnerCategory)   Spinner categorySpinner;
    @InjectView(R.id.spinnerUnit1)      Spinner unitSpinner1;
    @InjectView(R.id.spinnerUnit2)      Spinner unitSpinner2;
    @InjectView(R.id.inputText1)        TextView inputTextView1;
    @InjectView(R.id.inputText2)        TextView inputTextView2;

    private UnitConverterPresenter presenter;
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
        presenter = new UnitConverterPresenter(this);
        ButterKnife.inject(this, rootView);

        categorySpinner.setOnItemSelectedListener( new SpinnerAdapter());
        unitSpinner1.setOnItemSelectedListener(new SpinnerAdapter());
        unitSpinner2.setOnItemSelectedListener(new SpinnerAdapter());
        inputTextView1.addTextChangedListener(new TextViewAdapter(inputTextView1));
        inputTextView2.addTextChangedListener(new TextViewAdapter(inputTextView2));

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

    public void setSpinnerAdapter(ArrayAdapter adapter){
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

    private class TextViewAdapter implements TextWatcher{
        private View view;

        private TextViewAdapter(View view){
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
                if(view.getId() == inputTextView1.getId()){
                    String result = presenter.getConversionResult(
                            unitSpinner1.getSelectedItem(),
                            unitSpinner2.getSelectedItem(),
                            s.toString());
                    inputTextView2.setText(result);
                }else{
                    String result = presenter.getConversionResult(
                            unitSpinner2.getSelectedItem(),
                            unitSpinner1.getSelectedItem(),
                            s.toString());
                    inputTextView1.setText(result);
                }
            }
        }
    }

    private class SpinnerAdapter implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(parent.getId() == R.id.spinnerCategory){
                presenter.loadUnitArray(parent.getSelectedItem().toString());
            }else if(parent.getId() == R.id.spinnerUnit1 || parent.getId() == R.id.spinnerUnit2){
                inputTextView2.setText(presenter.getConversionResult(
                        unitSpinner1.getSelectedItem(),
                        unitSpinner2.getSelectedItem(),
                        inputTextView1.getText().toString()));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}