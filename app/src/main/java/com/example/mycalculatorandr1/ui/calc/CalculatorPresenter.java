package com.example.mycalculatorandr1.ui.calc;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Bundle;

import com.example.mycalculatorandr1.domain.Calculator;
import com.example.mycalculatorandr1.domain.Operation;

import java.math.BigDecimal;

public class CalculatorPresenter {

    private static final String KEY_STATE = "KEY_STATE";

    private CalculatorView view;
    private Calculator calculator;
    private EditValuePresenter editPresenter;

    private BigDecimal prevArg = null;
    private BigDecimal currentArg = new BigDecimal(0);
    private Operation prevOperation = null;

    public CalculatorPresenter(CalculatorView view, Calculator calculator) {
        this.view = view;
        this.calculator = calculator;
    }

    public void init(int maxEditLength) {
        editPresenter = new EditValuePresenter(view, maxEditLength);
    }

    public void onSaveState(Bundle bundle) {
        bundle.putParcelable(KEY_STATE, new State(prevArg, currentArg, prevOperation));
    }

    public void restoreState(Bundle bundle) {
        State state = bundle.getParcelable(KEY_STATE);

        prevArg = state.prevArg;
        currentArg = state.currentArg;
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
        //if (editPresenter.isDec())
            //view.setInfo(editPresenter.getStrValue());
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

    static class State implements Parcelable {
        public static final Creator<State> CREATOR = new Creator<State>() {
            @Override
            public State createFromParcel(Parcel in) {
                return new State(in);
            }

            @Override
            public State[] newArray(int size) {
                return new State[size];
            }
        };

        protected State(Parcel in) {
            if (in.readByte() == 0) {
                prevArg = null;
            } else {
                prevArg = new BigDecimal(in.readDouble());
            }
            if (in.readByte() == 0) {
                currentArg = null;
            } else {
                currentArg = new BigDecimal(in.readDouble());
            }
            if (in.readByte() == 0) {
                prevOperation = null;
            } else {
                prevOperation = Operation.EQUAL;
                for (Operation op: Operation.values()) {
                    if (op.getCode() == in.readInt()) {
                        prevOperation = op;
                    }
                }
            }
        }

        protected State(BigDecimal prevArg, BigDecimal currentArg, Operation prevOperation) {
            this.prevArg = prevArg;
            this.currentArg = currentArg;
            this.prevOperation = prevOperation;
        }

        BigDecimal prevArg = null;
        BigDecimal currentArg = new BigDecimal(0);
        Operation prevOperation = null;

        public BigDecimal getPrevArg() {
            return prevArg;
        }

        public BigDecimal getCurrentArg() {
            return currentArg;
        }

        public Operation getPrevOperation() {
            return prevOperation;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (prevArg == null) {
                dest.writeByte((byte)0);
            }
            else {
                dest.writeByte((byte)1);
                dest.writeDouble(prevArg.doubleValue());
            }
            if (currentArg == null) {
                dest.writeByte((byte)0);
            }
            else {
                dest.writeByte((byte)1);
                dest.writeDouble(currentArg.doubleValue());
            }
            if (prevOperation == null) {
                dest.writeByte((byte)0);
            }
            else  {
                dest.writeByte((byte)1);
                dest.writeInt(prevOperation.getCode());
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
