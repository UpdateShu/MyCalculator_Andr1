package com.example.mycalculatorandr1.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class State implements Parcelable {

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

    public State(BigDecimal prevArg, BigDecimal currentArg, Operation prevOperation) {
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
