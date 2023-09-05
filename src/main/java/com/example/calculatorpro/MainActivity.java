package com.example.calculatorpro;


import static com.example.calculatorpro.EvalJavaScript.calculateResult;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void display(View v){
        TextView screen = findViewById(R.id.screen);
        int button = v.getId();
        String buttonText = ((Button)findViewById(button)).getText().toString();
        String screenText = screen.getText().toString();
        screen.setText(screenText + buttonText);
    }

    public void delete(View v){
        TextView screen = findViewById(R.id.screen);
        String screenText = screen.getText().toString();
        screen.setText(screenText.substring(0,screenText.length() - 1));
    }
    public void clear(View v){
        TextView screen = findViewById(R.id.screen);
        String screenText = screen.getText().toString();
        screen.setText("");
    }

    public void product(View v){
        TextView screen = findViewById(R.id.screen);
        String screenText = screen.getText().toString();
        screen.setText(screenText + "*");
    }

  public void modulus(View v){
      TextView screen = findViewById(R.id.screen);
      String screenText = screen.getText().toString();
      screen.setText(screenText + "%");
  }

    public void sum(View v) {
        TextView screen = findViewById(R.id.screen);
        String screenText = screen.getText().toString();

        try {
            BigDecimal result = calculateResult(screenText);
            String finalResult = result.toPlainString(); // Convert BigDecimal to a plain string
            screen.setText(finalResult);
        } catch (ArithmeticException e) {
            // Handle math errors, for example, division by zero
            screen.setText("Math Error");
        } catch (IllegalArgumentException e) {
            // Handle invalid expressions or operators
            screen.setText("Invalid Expression");
        }
    }
}

