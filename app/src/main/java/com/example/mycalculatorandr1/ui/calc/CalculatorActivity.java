package com.example.mycalculatorandr1.ui.calc;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycalculatorandr1.R;
import com.example.mycalculatorandr1.domain.CalculatorImpl;
import com.example.mycalculatorandr1.domain.Operation;
import com.example.mycalculatorandr1.domain.Theme;
import com.example.mycalculatorandr1.storage.ThemeStorage;
import com.example.mycalculatorandr1.ui.theme.SelectThemeActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.HashMap;

public class CalculatorActivity extends AppCompatActivity implements CalculatorView {

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                       Theme theme = (Theme)result.getData().getSerializableExtra(SelectThemeActivity.EXTRA_THEME);

                       storage.saveTheme(theme);
                       recreate();
                    }
                }
            });

    private CalculatorPresenter presenter;
    private ThemeStorage storage;

    private TextView txtInput;
    private int maxEditLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage = new ThemeStorage(this);
        setTheme(storage.getSavedTheme().getTheme());

        setContentView(R.layout.activity_calculator);
        txtInput = findViewById(R.id.input);
        maxEditLength = 12;

        presenter = new CalculatorPresenter(this, new CalculatorImpl(), maxEditLength);
        if (savedInstanceState != null) {
            presenter.restoreState(savedInstanceState);
        }

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

        findViewById(R.id.choose_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculatorActivity.this, SelectThemeActivity.class);
                intent.putExtra(SelectThemeActivity.EXTRA_THEME, storage.getSavedTheme());
                launcher.launch(intent);
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveState(outState);
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