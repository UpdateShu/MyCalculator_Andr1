package com.example.mycalculatorandr1.domain;

import android.os.Parcel;
import android.os.Parcelable;

public enum Operation {
    PLUS(1),
    MINUS(2),
    MULTY(3),
    DIV(4),
    EQUAL(5);

    public int getCode() {
        return code;
    }

    private final int code;
    private Operation(int code) {
        this.code = code;
    }
}