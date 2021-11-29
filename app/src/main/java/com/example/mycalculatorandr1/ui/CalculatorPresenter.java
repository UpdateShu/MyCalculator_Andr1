package com.example.mycalculatorandr1.ui;

import com.example.mycalculatorandr1.domain.Calculator;
import com.example.mycalculatorandr1.domain.Operation;

import java.math.BigDecimal;

public class CalculatorPresenter {
    private CalculatorView view;
    private Calculator calculator;
    private EditValuePresenter editPresenter;

    private BigDecimal prevArg = null;
    private BigDecimal currentArg = new BigDecimal(0);
    private Operation prevOperation = null;

    public CalculatorPresenter(CalculatorView view, Calculator calculator, int maxEditLength) {
        this.view = view;
        this.calculator = calculator;

        editPresenter = new EditValuePresenter(view, maxEditLength);
    }

    public void onBackPressed() {
        String str = view.getEditValue();
        if (!str.equals("0")) {
            if (str.length() != 1) {
                view.setEditValue(str.substring(0, str.length() - 1));
            }
            else {
                editPresenter.reset();
                currentArg = null;
            }
        }
    }

    public void onDotPressed() {
        if (currentArg == null) {
            editPresenter.reset();
        }
        editPresenter.setDot();
        currentArg = editPresenter.getEditValue();
    }

    public void OnDigitPressed(Integer digit) {
        if (editPresenter.isDec())
            view.setInfo(editPresenter.getStrValue());
        if (currentArg == null) {
            editPresenter.reset();
        }
        editPresenter.setDigit(digit);
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
