package com.example.fromjsonrecycleview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Product> products = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.productRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        fetchProducts();
    }

    private void fetchProducts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://dummyjson.com/products");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    response.append(reader.readLine());

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray productsArray = jsonResponse.getJSONArray("products");

                    for (int i = 0; i < productsArray.length(); i++) {
                        JSONObject productJson = productsArray.getJSONObject(i);
                        Product product = new Product(
                                productJson.getString("title"),
                                productJson.getString("description"),
                                productJson.getDouble("price"),
                                productJson.getDouble("rating"),
                                productJson.getString("thumbnail")
                        );
                        products.add(product);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!products.isEmpty()) {
                                Log.i("TAG", "run: "+ products.size());
                                recyclerView.setAdapter(new ProductAdapter(MainActivity.this, products));
                            } else {
                                Toast.makeText(MainActivity.this, "No products found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static class Product {
        private final String title, description, thumbnail;
        private final double price, rating;

        public Product(String title, String description, double price, double rating, String thumbnail) {
            this.title = title;
            this.description = description;
            this.price = price;
            this.rating = rating;
            this.thumbnail = thumbnail;
        }

        public String getTitle()
        {
            return title;
        }
        public String getDescription()
        {
            return description;
        }
        public double getPrice()
        {
            return price; }
        public double getRating() {
            return rating;
        }
        public String getThumbnail()
        {
            return thumbnail;
        }
    }
}