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

    public CalculatorPresenter(CalculatorView view, Calculator calculator) {
        this.view = view;
        this.calculator = calculator;

        editPresenter = new EditValuePresenter(view);
    }

    public void onDotPressed() {
        editPresenter.setDot();
        currentArg = editPresenter.getEditValue();
    }

    public void OnDigitPressed(Integer digit) {
        editPresenter.setDigit(digit);
        currentArg = editPresenter.getEditValue();
    }

    public void onOperandPressedPressed(Operation operation) {
        if (prevArg != null) {
            currentArg = editPresenter.getEditValue();
            double result = calculator.performOperation(prevArg.doubleValue(), currentArg.doubleValue(), prevOperation);
            view.setEditValue((new BigDecimal(result)).toString());
        }
        else {
            prevArg = editPresenter.getEditValue();
            prevOperation = operation;

            currentArg = new BigDecimal(0);
            editPresenter.reset();
        }
    }
}
