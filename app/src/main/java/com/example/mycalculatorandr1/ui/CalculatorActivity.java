package com.example.mycalculatorandr1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycalculatorandr1.R;
import com.example.mycalculatorandr1.domain.CalculatorImpl;
import com.example.mycalculatorandr1.domain.Operation;

import java.util.HashMap;

public class CalculatorActivity extends AppCompatActivity implements CalculatorView {

    private CalculatorPresenter presenter;

    private TextView txtInput, txtInfo;
    private int maxEditLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        txtInput = findViewById(R.id.input);
        txtInfo = findViewById(R.id.info);
        maxEditLength = 12;

        presenter = new CalculatorPresenter(this, new CalculatorImpl(), maxEditLength);

        findViewById(R.id.key_dec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDotPressed();
            }
        });
        initDigits();
        initOperands();

        findViewById(R.id.key_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackPressed();
            }
        });

        logLifecycle("onCreate");
        if (savedInstanceState == null) {
            logLifecycle("onCreate first");
        }
        else {
            logLifecycle("onCreate recreate");
        }
    }

    private void initDigits() {

        HashMap<Integer, Integer> digits = new HashMap<>();
        digits.put(R.id.key_0, 0);
        digits.put(R.id.key_1, 1);
        digits.put(R.id.key_2, 2);
        digits.put(R.id.key_3, 3);
        digits.put(R.id.key_4, 4);
        digits.put(R.id.key_5, 5);
        digits.put(R.id.key_6, 6);
        digits.put(R.id.key_7, 7);
        digits.put(R.id.key_8, 8);
        digits.put(R.id.key_9, 9);

        View.OnClickListener digitClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnDigitPressed(digits.get(v.getId()));
            }
        };
        findViewById(R.id.key_0).setOnClickListener(digitClickListener);
        findViewById(R.id.key_1).setOnClickListener(digitClickListener);
        findViewById(R.id.key_2).setOnClickListener(digitClickListener);
        findViewById(R.id.key_3).setOnClickListener(digitClickListener);
        findViewById(R.id.key_4).setOnClickListener(digitClickListener);
        findViewById(R.id.key_5).setOnClickListener(digitClickListener);
        findViewById(R.id.key_6).setOnClickListener(digitClickListener);
        findViewById(R.id.key_7).setOnClickListener(digitClickListener);
        findViewById(R.id.key_8).setOnClickListener(digitClickListener);
        findViewById(R.id.key_9).setOnClickListener(digitClickListener);
    }

    private void initOperands() {

        HashMap<Integer, Operation> operands = new HashMap<>();
        operands.put(R.id.key_plus, Operation.PLUS);
        operands.put(R.id.key_minus, Operation.MINUS);
        operands.put(R.id.key_div, Operation.DIV);
        operands.put(R.id.key_multy, Operation.MULTY);

        View.OnClickListener operandClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onOperandPressed(operands.get(v.getId()));
            }
        };
        findViewById(R.id.key_multy).setOnClickListener(operandClickListener);
        findViewById(R.id.key_plus).setOnClickListener(operandClickListener);
        findViewById(R.id.key_minus).setOnClickListener(operandClickListener);
        findViewById(R.id.key_div).setOnClickListener(operandClickListener);
        findViewById(R.id.key_equal).setOnClickListener(operandClickListener);
    }

    @Override
    public String getEditValue() {
        return txtInput.getText().toString();
    }

    @Override
    public void setEditValue(String strVal) {
        String formatValue = strVal != null
                ? (strVal.length() <= maxEditLength ? strVal : strVal.substring(0, maxEditLength)) : "0";
        txtInput.setText(formatValue);
    }

    @Override
    public void setInfo(String info) {
        txtInfo.setText(info);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logLifecycle("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logLifecycle("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logLifecycle("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logLifecycle("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logLifecycle("onDestroy");
    }

    private void logLifecycle(String toLog) {
        Log.d("logLifecycle", toLog);
    }
}