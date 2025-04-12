package com.example.parsingfromjson;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView titleTextView, priceTextView, descriptionTextView;
    private ImageView productImageView;
    private RatingBar ratingBar;
    private ImageButton leftArrow, rightArrow;

    private ArrayList<Product> products = new ArrayList<>();
    private int currentPosition = 0;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTextView = findViewById(R.id.TitleTextView);
        priceTextView = findViewById(R.id.PriceTextView);
        descriptionTextView = findViewById(R.id.DescripctionTextView);
        productImageView = findViewById(R.id.imageView);
        ratingBar = findViewById(R.id.ratingBar);
        leftArrow = findViewById(R.id.leftArrow);
        rightArrow = findViewById(R.id.rightArrow);

        new Thread(new Runnable() {
            @Override
            public void run() {
                fetchProducts();
            }
        }).start();

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviousProduct();
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextProduct();
            }
        });
    }

    private void fetchProducts() {
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
                ratingBar.setEnabled(false);
                products.add(product);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (!products.isEmpty()) {
                        displayProduct(currentPosition);
                    } else {
                        Toast.makeText(MainActivity.this, "No products found", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayProduct(int position) {

        Product product = products.get(position);
        titleTextView.setText(product.getTitle());
        descriptionTextView.setText(product.getDescription());
        priceTextView.setText(String.format("$%.2f", product.getPrice()));
        ratingBar.setRating((float) product.getRating());

        new DownloadImageTask().execute(product.getThumbnail());
    }

    private void showNextProduct() {
        if (currentPosition < products.size()-1) {
            currentPosition++;
            displayProduct(currentPosition);
        }
    }

    private void showPreviousProduct() {
        if (currentPosition > 0) {
            currentPosition--;
            displayProduct(currentPosition);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                productImageView.setImageBitmap(result);
            } else {
                productImageView.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        }
    }

    private static class Product {
        private final String title;
        private final String description;
        private final double price;
        private final double rating;
        private final String thumbnail;

        public Product(String title, String description, double price, double rating, String thumbnail) {
            this.title = title;
            this.description = description;
            this.price = price;
            this.rating = rating;
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public double getPrice() {
            return price;
        }

        public double getRating() {

            return rating;
        }

        public String getThumbnail() {
            return thumbnail;
        }
    }
}
