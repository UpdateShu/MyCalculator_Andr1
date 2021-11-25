package com.example.mycalculatorandr1.domain;

public class CalculatorImpl implements Calculator {
    @Override
    public double performOperation(double argOne, double argTwo, Operation operation) {
        switch (operation) {
            case PLUS:
                return argOne + argTwo;
            case MINUS:
                return argOne - argTwo;
            case MULTY:
                return argOne * argTwo;
            case DIV:
                return argOne / argTwo;
        }
        return 0;
    }
}
