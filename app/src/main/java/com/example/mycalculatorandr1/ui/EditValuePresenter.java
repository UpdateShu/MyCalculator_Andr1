package com.example.mycalculatorandr1.ui;

import java.math.BigDecimal;

public class EditValuePresenter {
    private CalculatorView view;

    public EditValuePresenter(CalculatorView view) {
        this.view = view;
        reset();
    }

    private String getStrValue() {
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

    private int getIntValue() {
        if (isDec()) {
            return Integer.parseInt(getStrValue().substring(0, getDotIndex()));
        } else {
            return Integer.parseInt(getStrValue());
        }
    }

    private BigDecimal getDecValue() {
        String decAfterDot = getStrValue().substring(getDotIndex() + 1);
        BigDecimal currentBd = new BigDecimal(Integer.parseInt(decAfterDot));
        for (int i = 0; i < decAfterDot.length(); i++) {
            currentBd.divide(BigDecimal.TEN);
        }
        return currentBd;
    }
}
