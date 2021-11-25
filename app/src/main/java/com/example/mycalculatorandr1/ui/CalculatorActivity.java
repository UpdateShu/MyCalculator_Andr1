package com.example.mycalculatorandr1.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycalculatorandr1.R;
import com.example.mycalculatorandr1.domain.CalculatorImpl;
import com.example.mycalculatorandr1.domain.Operation;

import java.util.HashMap;

public class CalculatorActivity extends AppCompatActivity implements CalculatorView {

    private CalculatorPresenter presenter;

    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        txtResult = findViewById(R.id.input);
        presenter = new CalculatorPresenter(this, new CalculatorImpl());

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
                try {
                    presenter.OnDigitPressed(digits.get(v.getId()));
                }
                catch (Exception ex)
                {
                    logLifecycle(ex.toString());
                }
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

        findViewById(R.id.key_dec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDotPressed();
            }
        });

        HashMap<Integer, Operation> operands = new HashMap<>();
        operands.put(R.id.key_plus, Operation.PLUS);
        operands.put(R.id.key_minus, Operation.MINUS);
        operands.put(R.id.key_div, Operation.DIV);
        operands.put(R.id.key_multy, Operation.MULTY);

        View.OnClickListener operandClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onOperandPressedPressed(operands.get(v.getId()));
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
        return txtResult.getText().toString();
    }

    @Override
    public void setEditValue(String value) {
        txtResult.setText(value != null ? value : "0");
    }

    private void logLifecycle(String toLog) {
        Log.d("logLifecycle", toLog);
    }
}