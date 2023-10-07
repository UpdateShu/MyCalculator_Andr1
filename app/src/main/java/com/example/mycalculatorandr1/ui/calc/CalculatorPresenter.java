package com.example.mycalculatorandr1.ui.calc;

import android.os.Bundle;

import com.example.mycalculatorandr1.domain.Calculator;
import com.example.mycalculatorandr1.domain.Operation;
import com.example.mycalculatorandr1.domain.State;

import java.math.BigDecimal;

public class CalculatorPresenter {

    private static final String KEY_STATE = "KEY_STATE";

    private CalculatorView view;
    private Calculator calculator;

    private EditValuePresenter editPresenter;

    private BigDecimal prevArg = null;
    private BigDecimal currentArg = new BigDecimal(0);
    public BigDecimal getCurrentArg() {
        return currentArg;
    }

    private Operation prevOperation = null;

    public CalculatorPresenter(
            CalculatorView view, Calculator calculator, int maxEditLength) {
        this.view = view;
        this.calculator = calculator;

        editPresenter = new EditValuePresenter(view, maxEditLength);
    }

    public void onSaveState(Bundle bundle) {
        bundle.putParcelable(KEY_STATE, new State(prevArg, currentArg, prevOperation));
    }

    public void restoreState(Bundle bundle) {
        State state = bundle.getParcelable(KEY_STATE);

        prevArg = state.getPrevArg();
        currentArg = state.getCurrentArg();
        if (currentArg != null) {
            setEditValue(currentArg.toString());
        }
    }

    private void setEditValue(String editValue) {
        view.setEditValue(editValue);
    }

    public void onBackPressed() {
        String str = view.getEditValue();
        if (!str.equals("0")) {
            if (str.length() != 1) {
                setEditValue(str.substring(0, str.length() - 1));
            }
            else {
                editPresenter.reset();
                currentArg = null;
            }
        }
    }

    public BigDecimal onDigitPressed(int digit) {
        if (currentArg == null) {
            editPresenter.reset();
        }
        editPresenter.setDigit(digit);
        currentArg = editPresenter.getEditValue();
        return currentArg;
    }

    public void onDotPressed() {
        if (currentArg == null) {
            editPresenter.reset();
        }
        editPresenter.setDot();
        currentArg = editPresenter.getEditValue();
    }

    public void onOperandPressed(Operation operation) {
        if (prevArg != null) {
            if (currentArg != null) {
                currentArg = editPresenter.getEditValue();
                double result = calculator.performOperation(prevArg.doubleValue(), currentArg.doubleValue(), prevOperation);

                view.setEditValue((new BigDecimal(result)).toString());
                prevArg = editPresenter.getEditValue();
                currentArg = null;
            }
        }
        else {
            prevArg = editPresenter.getEditValue();
            currentArg = null;
        }
        prevOperation = operation;
    }
}
