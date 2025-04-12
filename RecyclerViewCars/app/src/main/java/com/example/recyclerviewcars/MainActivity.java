package com.example.recyclerviewcars;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Car> carList = new ArrayList<>();
        carList.add(new Car("Toyota Camry", "Sedan", R.drawable.carone));
        carList.add(new Car("Honda Civic", "Compact", R.drawable.cartwo));
        carList.add(new Car("Ford Mustang", "Sports", R.drawable.carthree));
        carList.add(new Car("Tesla Model S", "Electric", R.drawable.carfour));
        carList.add(new Car("Chevrolet Tahoe", "SUV", R.drawable.carfive));
        carList.add(new Car("Toyota Camry", "Sedan", R.drawable.carsix));
        carList.add(new Car("Honda Civic", "Compact", R.drawable.carseven));
        carList.add(new Car("Ford Mustang", "Sports", R.drawable.careight));
        carList.add(new Car("Tesla Model S", "Electric", R.drawable.carnine));
        carList.add(new Car("Chevrolet Tahoe", "SUV", R.drawable.carten));
        carList.add(new Car("Toyota Camry", "Sedan", R.drawable.careleven));
        carList.add(new Car("Honda Civic", "Compact", R.drawable.cartwelve));
        carList.add(new Car("Ford Mustang", "Sports", R.drawable.cartherteen));

        CarAdapter adapter = new CarAdapter(carList);
        recyclerView.setAdapter(adapter);
    }
}