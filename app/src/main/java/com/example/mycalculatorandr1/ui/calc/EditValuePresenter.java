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

    private String _strValue = "0";
    public String getStrValue() {
        return _strValue;
    }

    public void setStrValue(String strValue) {
        view.setEditValue(strValue);
        _strValue = strValue;
    }

    private int getDotIndex() {
        String str = getStrValue();
        return str != null ? str.indexOf('.') : -1;
    }

    public boolean isDec() {
        return getDotIndex() != -1;
    }

    public void reset() { setStrValue("0"); }

    public void setDot() {
        if (!isDec()) {
            setStrValue(getStrValue() + ".0");
        }
    }

    public void setDigit(int digit) {
        String strVal = getStrValue();
        if (strVal == null || strVal == "" || strVal.equals("0")) {
            setStrValue(Integer.toString(digit));
        }
        else {
            if (isDec()) {
                setStrValue(getIntValue() + "." + digit);
            }
            else {
                setStrValue(getStrValue() + digit);
            }
        }
    }

    public BigDecimal getEditValue() {
        if (!isDec()) {
            String str = getStrValue();
            return new BigDecimal(Integer.parseInt(str != null && str != "" ? str : "0"));
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
