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

    public EditValuePresenter getEditPresenter() { return _editPresenter; }
    private EditValuePresenter _editPresenter;

    private BigDecimal prevArg = null;
    private BigDecimal currentArg = new BigDecimal(0);
    private Operation prevOperation = null;

    public CalculatorPresenter(
            CalculatorView view, Calculator calculator, int maxEditLength) {
        this.view = view;
        this.calculator = calculator;

        _editPresenter = new EditValuePresenter(view, maxEditLength);
    }

    public void onSaveState(Bundle bundle) {
        bundle.putParcelable(KEY_STATE, new State(prevArg, currentArg, prevOperation));
    }

    public void restoreState(Bundle bundle) {
        State state = bundle.getParcelable(KEY_STATE);

        prevArg = state.getPrevArg();
        currentArg = state.getCurrentArg();
        if (currentArg != null) {
            view.setEditValue(currentArg.toString());
        }
    }

    public void onBackPressed() {
        String str = view.getEditValue();
        if (!str.equals("0")) {
            if (str.length() != 1) {
                view.setEditValue(str.substring(0, str.length() - 1));
            }
            else {
                _editPresenter.reset();
                currentArg = null;
            }
        }
    }

    public Boolean test() {
        return true;
    }

    public void onDotPressed() {
        //if (currentArg == null) {
            _editPresenter.reset();
        /*}
        _editPresenter.setDot();
        currentArg = _editPresenter.getEditValue();*/
    }

    public void OnDigitPressed(int digit) {
        //if (editPresenter.isDec())
            //view.setInfo(editPresenter.getStrValue());
        if (currentArg == null) {
            _editPresenter.reset();
        }
        _editPresenter.setDigit(digit);
        currentArg = _editPresenter.getEditValue();
    }

    public void onOperandPressed(Operation operation) {
        if (prevArg != null) {
            if (currentArg != null) {
                currentArg = _editPresenter.getEditValue();
                double result = calculator.performOperation(prevArg.doubleValue(), currentArg.doubleValue(), prevOperation);

                view.setEditValue((new BigDecimal(result)).toString());
                prevArg = _editPresenter.getEditValue();
                currentArg = null;
            }
        }
        else {
            prevArg = _editPresenter.getEditValue();
            currentArg = null;
        }
        prevOperation = operation;
    }
}
