package com.example.mycalculatorandr1.ui.calc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EditValuePresenter {

    private CalculatorView view;
    private int maxLength;

    public EditValuePresenter(CalculatorView view, int maxLength) {
        this.view = view;
        this.maxLength = maxLength;
        reset();
    }

    public String getStrValue() {
        return view.getEditValue();
    }

    private int getDotIndex() {
        return getStrValue().indexOf('.');
    }

    public boolean isDec() {
        return getDotIndex() != -1;
    }

    public void reset() {
        view.setEditValue("0");
    }

    public void setDot() {
        if (!isDec()) {
            view.setEditValue(view.getEditValue() + ".0");
        }
    }

    public void setDigit(int digit) {
        String strVal = view.getEditValue();
        if (strVal.equals("0")) {
            view.setEditValue(Integer.toString(digit));
        }
        else {
            if (isDec()) {
                BigDecimal decVal = getDecValue();
                if (decVal.equals(BigDecimal.ZERO)) {
                    view.setEditValue(getIntValue() + "." + Integer.toString(digit));
                }
                else {
                    view.setEditValue(view.getEditValue() + Integer.toString(digit));
                }
            }
            else {
                view.setEditValue(view.getEditValue() + Integer.toString(digit));
            }
        }
    }

    public BigDecimal getEditValue() {
        if (!isDec()) {
            return new BigDecimal(Integer.parseInt(getStrValue()));
        }
        else {
            BigDecimal decValue = getDecValue();
            return decValue.add(new BigDecimal(getIntValue()));
        }
    }

    public int getIntValue() {
        if (isDec()) {
            return Integer.parseInt(getStrValue().substring(0, getDotIndex()));
        } else {
            return Integer.parseInt(getStrValue());
        }
    }

    public BigDecimal getDecValue() {
        String decAfterDot = getStrValue().substring(getDotIndex() + 1);
        BigDecimal decVal = new BigDecimal(decAfterDot);
        int length = Math.min(decAfterDot.length(), maxLength - 2);
        for (int i = 0; i < length; i++) {
            decVal = decVal.divide(BigDecimal.TEN, maxLength, RoundingMode.HALF_UP);
        }
        return decVal;
    }
}
