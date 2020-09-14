package com.example.kalkulyator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity {


    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, buttonQavs2, buttonPlus, buttonMinus, buttonMultiply, buttonDivision, buttonEqual, buttonClear, buttonDot, buttonQavs1;
    //textViewInputda ekranga kiritilayotgan amallar aks etadi. TextVIewOutputda natija aks etadi
    TextView textViewInput, textViewOutput;
    String process;
    boolean check = false;
    boolean lastDot = true;
    boolean lastNumber = false;
    boolean tekshiruvchi = false;
    boolean tozalovchi = false;
    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      // Tugmalar Id yordamida topib olinadi
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        buttonPlus = findViewById(R.id.buttonPlus);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonDivision = findViewById(R.id.buttonDivision);
        buttonMultiply = findViewById(R.id.buttonMultiply);

        buttonEqual = findViewById(R.id.buttonEqual);

        buttonClear = findViewById(R.id.buttonClear);
        buttonDot = findViewById(R.id.buttonDot);
        buttonQavs2 = findViewById(R.id.buttonQavs2);
        buttonQavs1 = findViewById(R.id.buttonQavs1);
        textViewInput = findViewById(R.id.tvInput);
        textViewOutput = findViewById(R.id.tvOutput);
     //Agar Delete tugmasi bosilsa o'chiradi
        buttonClear.setOnClickListener(v -> {
            if (textViewInput.getText().equals("")) {
                textViewInput.setText("");
            } else {
                // len nomli o'zgaruvchida textViewInputni uzunligi saqlanadi
                int len = textViewInput.getText().length();
                // s da textViewInput dagi matn saqlanadi
                String s = textViewInput.getText().toString();
                // Agar textViewInput ni uzunligi 0 dan katta bo'lsa quyidagi ammalarni bajaradi
                if (len > 0) {
                    textViewInput.setTextColor(getResources().getColor(R.color.forTextView));
                    textViewOutput.setTextColor(getResources().getColor(R.color.forTextView));
                    textViewInput.setText(textViewInput.getText().subSequence(0, textViewInput.getText().length() - 1));
                    textViewOutput.setText("");
                    lastDot = true;
                    tekshiruvchi = true;
                }

            }
        });
        buttonClear.setOnLongClickListener(view -> {
            Clear();
            textViewInput.setTextColor(getResources().getColor(R.color.forTextView));
            textViewOutput.setTextColor(getResources().getColor(R.color.forTextView));
            lastDot = true;
            lastNumber = false;
            check = false;
            tekshiruvchi = false;
            return true;
        });

        for (Button btn : new Button[]{
                btn0, btn2, btn3, btn4, btn5, btn6,
                btn7, btn8, btn9, btn0, buttonQavs1, buttonQavs2
        }) {
            btn.setOnClickListener(v -> {
                if (tozalovchi){
                    Clear();
                }
                tozalovchi = false;
                lastNumber = true;
                process = textViewInput.getText().toString();
                textViewInput.setText(process + ((Button) v).getText().toString());
                tekshiruvchi = true;
            });
        }

        buttonPlus.setOnClickListener(v -> {
            if (tekshiruvchi) {
                lastDot = true;
                lastNumber = false;
                process = textViewInput.getText().toString();
                textViewInput.setText(process + "+");
                tekshiruvchi = false;
            }
        });


        buttonMinus.setOnClickListener(v -> {
            if (tekshiruvchi) {
                lastDot = true;
                lastNumber = false;
                process = textViewInput.getText().toString();
                textViewInput.setText(process + "-");
                tekshiruvchi = false;
            }
        });

        buttonMultiply.setOnClickListener(v -> {
            if (tekshiruvchi) {
                lastDot = true;
                lastNumber = false;
                process = textViewInput.getText().toString();
                textViewInput.setText(process + "×");
                tekshiruvchi = false;
            }
        });

        buttonDivision.setOnClickListener(v -> {
            if (tekshiruvchi) {
                lastDot = true;
                lastNumber = false;
                process = textViewInput.getText().toString();
                textViewInput.setText(process + "÷");
                tekshiruvchi = false;
            }
        });

        buttonDot.setOnClickListener(view -> {
            if (lastDot && lastNumber) {
                process = textViewInput.getText().toString();
                textViewInput.setText(process + ".");
                lastDot = false;
            } else {
                process = textViewInput.getText().toString();
                textViewInput.setText(process + "");
            }
        });

        buttonEqual.setOnClickListener(v -> {
            process = textViewInput.getText().toString();

            process = process.replaceAll("×", "*");
            process = process.replaceAll("%", "/100");
            process = process.replaceAll("÷", "/");


            Context rhino = Context.enter();

            rhino.setOptimizationLevel(-1);

            String finalResult = "";

            try {
                Scriptable scriptable = rhino.initStandardObjects();
                finalResult = rhino.evaluateString(scriptable, process, "javascript", 1, null).toString();
            } catch (Exception e) {
                finalResult = "Xato ifoda";
                textViewInput.setTextColor(getResources().getColor(R.color.red));
                textViewOutput.setTextColor(getResources().getColor(R.color.red));
            }
            textViewOutput.setText(finalResult);

            tozalovchi = true;
        });
    }

    private void Clear (){
        textViewInput.setText("");
        textViewOutput.setText("");
    }
}
