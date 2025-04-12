package com.example.demoapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtPhone;
    private EditText edtMsg;
    private Button btnNext;
    private Button btnExit;
    private TextView txtCounter;

    private static final String TAG = "MainActivity";
    private static final String COUNTER_KEY = "counter";
    private int counter = 0;

    @SuppressLint("SetTextI18n")




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnExit = findViewById(R.id.btnExit);
        btnNext = findViewById(R.id.btnNext);
        edtPhone = findViewById(R.id.edtPhone);
        edtMsg = findViewById(R.id.edtMsg);
        txtCounter = findViewById(R.id.txtCounter);

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(COUNTER_KEY, 0);
        }
        txtCounter.setText("Rotation Count: " + counter);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("MOBILE_NUMBER", edtPhone.getText().toString());
                intent.putExtra("MESSAGE", edtMsg.getText().toString());
                startActivity(intent);
            }
        });

        Log.i(TAG, "onCreate: Counter = " + counter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        counter++;
        outState.putInt(COUNTER_KEY, counter);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter++;
        txtCounter.setText("Rotation Count: " + counter);
    }
    public void exit(View view) {
        finish();
    }
}
