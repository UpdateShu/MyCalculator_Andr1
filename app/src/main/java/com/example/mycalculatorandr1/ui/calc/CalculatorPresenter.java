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

    public EditValuePresenter editPresenter;
    public String getEditStr() {
        return editPresenter.getStrValue();
    }

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
            editPresenter.setStrValue(currentArg.toString());
        }
    }

    public void onBackPressed() {
        String str = view.getEditValue();
        if (!str.equals("0")) {
            if (str.length() != 1)
            {
                editPresenter.setStrValue(str.substring(0, str.length() - 1));
                currentArg = editPresenter.getEditValue();
            }
            else {
                editPresenter.reset();
                currentArg = null;
            }
        }
    }

    public void onDigitPressed(int digit) {
        if (currentArg == null) {
            editPresenter.reset();
        }
        editPresenter.setDigit(digit);
        currentArg = editPresenter.getEditValue();
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

                editPresenter.setStrValue((new BigDecimal(result)).toString());
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
