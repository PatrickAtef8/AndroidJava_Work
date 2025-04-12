package com.example.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StaticFragment extends Fragment {

    private int counter = 0;
    Communicator communicatorRef;

    public StaticFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter_value", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_static, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View fragmentLayout, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(fragmentLayout, savedInstanceState);
        communicatorRef = (Communicator) getActivity();

        Button btn = fragmentLayout.findViewById(R.id.staticButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                if (communicatorRef != null) {
                    communicatorRef.count(counter);
                }
            }
        });
        fragmentLayout.post(() -> {
            if (communicatorRef != null) {
                communicatorRef.count(counter);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter_value", counter);
    }
    public void resetCounter() {
        counter = 0;

        if (communicatorRef != null) {
            communicatorRef.count(counter);
        }
    }

}
