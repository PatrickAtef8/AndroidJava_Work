package com.example.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DynamicFragment extends Fragment {

    TextView txt;
    int counter = 0;  // save the last counter value

    public DynamicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txt = view.findViewById(R.id.txtCounter);

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter_value", 0);
        }

        txt.setText(String.valueOf(counter));
    }

    public void updateCounter(int counter) {
        this.counter = counter;
        if (txt != null) {
            txt.setText(String.valueOf(counter));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter_value", counter);
    }
}
