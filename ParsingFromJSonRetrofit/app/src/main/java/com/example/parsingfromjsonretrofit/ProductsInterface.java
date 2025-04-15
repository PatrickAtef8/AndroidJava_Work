package com.example.parsingfromjsonretrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductsInterface {
    @GET("products")
    Call<MainActivity.ProductResponse> getProducts();
}