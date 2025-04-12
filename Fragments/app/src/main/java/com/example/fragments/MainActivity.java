package com.example.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements Communicator {
    FragmentManager mgr;
    DynamicFragment dynamicFragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mgr = getSupportFragmentManager();





        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnRmv = findViewById(R.id.btnRmv);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Only add fragment if it doesn't exist already
                    dynamicFragment = new DynamicFragment();
                    transaction = mgr.beginTransaction();
                    transaction.add(R.id.dynamicContainerView, dynamicFragment, "DynamicFragment");
                    transaction.commit();
                }
        });


        btnRmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     transaction = mgr.beginTransaction();
                     transaction.remove(dynamicFragment);
                     transaction.commit();

                }

        });



        if (savedInstanceState != null) {
            dynamicFragment = (DynamicFragment) mgr.findFragmentByTag("DynamicFragment");
        }
    }

    @Override
    public void count(int counter){

        if (dynamicFragment != null){
        dynamicFragment.updateCounter(counter);
        }
    }
}

