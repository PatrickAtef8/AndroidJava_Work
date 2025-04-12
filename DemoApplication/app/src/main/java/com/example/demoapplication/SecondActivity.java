package com.example.demoapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    private static final String PREF_FILE = "PREF_FILE";
    private static final String PRIVATE_FILE = "PRIVATE_FILE";

    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String MESSAGE = "MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView txtMobile;
        TextView txtMessage;
        Button btnBack;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        txtMobile = findViewById(R.id.txtMobile);
        txtMessage = findViewById(R.id.txtMessage);
        btnBack = findViewById(R.id.btnBack);
        Button btnWsh = findViewById(R.id.btnWsh);
        Button btnRSh = findViewById(R.id.btnRSh);
        Button btnWIs = findViewById(R.id.btnWIs);
        Button btnRIs = findViewById(R.id.RIs);



        Intent incomingIntent = getIntent();
        String mobile = incomingIntent.getStringExtra(SecondActivity.MOBILE_NUMBER);
        String msg = incomingIntent.getStringExtra(SecondActivity.MESSAGE);
        txtMobile.setText(mobile);
        txtMessage.setText(msg);

    btnBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });



    btnWsh.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences preferences = getSharedPreferences(SecondActivity.PREF_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =preferences.edit();
            editor.putString(SecondActivity.MOBILE_NUMBER,txtMobile.getText().toString());
            editor.putString(SecondActivity.MESSAGE,txtMessage.getText().toString());
            editor.commit();
            txtMobile.setText("");
            txtMessage.setText("");

        }

    });




    btnRSh.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences preferences = getSharedPreferences(SecondActivity.PREF_FILE, Context.MODE_PRIVATE);
            txtMobile.setText(preferences.getString(SecondActivity.MOBILE_NUMBER,getString(R.string.n_a)));
            txtMessage.setText(preferences.getString(SecondActivity.MESSAGE, getString(R.string.n_a)));

        }
    });


    btnWIs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          try {
              FileOutputStream fos =  openFileOutput(SecondActivity.PRIVATE_FILE,Context.MODE_PRIVATE);
                DataOutputStream dos =  new DataOutputStream(fos);
                dos.writeUTF(txtMobile.getText().toString());
                dos.writeUTF((txtMessage.getText().toString()));
                dos.close();
                fos.close();

              txtMobile.setText("");
              txtMessage.setText("");

            } catch (FileNotFoundException e) {
                Toast.makeText(SecondActivity.this, "Couldn't locate the file", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                Toast.makeText(SecondActivity.this, "Couldn't Create the file", Toast.LENGTH_SHORT).show();
            }

        }
    });
    btnRIs.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FileInputStream fis = null;
            try {
                fis = openFileInput(SecondActivity.PRIVATE_FILE);
                DataInputStream dis = new DataInputStream(fis);
                try {
                    txtMobile.setText(dis.readUTF());
                } catch (IOException e) {
                    Toast.makeText(SecondActivity.this, "Couldn't Read the file", Toast.LENGTH_SHORT).show();
                }
                try {
                    txtMessage.setText(dis.readUTF());
                } catch (IOException e) {
                    Toast.makeText(SecondActivity.this, "Couldn't Read the file", Toast.LENGTH_SHORT).show();
                }


            } catch (FileNotFoundException e) {
                Toast.makeText(SecondActivity.this, "Couldn't locate the file", Toast.LENGTH_SHORT).show();
            }
        }
    });


        Button btWDB = findViewById(R.id.btnWDB);
    btWDB.setOnClickListener(view ->{
        MessageAdapter adapter = new MessageAdapter(this);
 long id = adapter.insertMessage(new Message(txtMobile.getText().toString(),txtMessage.getText().toString()));

        txtMessage.setText("");
        });


        Button btRDB = findViewById(R.id.btnRDB);
        btRDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MessageAdapter adapter = new MessageAdapter(SecondActivity.this);
                    String mobile = txtMobile.getText().toString();
                    Message msg = adapter.findMessage(mobile);

                    if (msg != null) {
                        txtMessage.setText(msg.getMessage());
                    } else {
                        Toast.makeText(SecondActivity.this, "No message found for this number", Toast.LENGTH_SHORT).show();
                        txtMessage.setText("");
                    }
                } catch (Exception e) {
                    Log.e("DB_ERROR", "Error reading message from DB", e);
                    Toast.makeText(SecondActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    }