package com.example.quizzapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface ApiService {

    // GET all users
    @GET("users")
    Call<List<User>> getUsers();

    // POST a new user
    @POST("users")
    Call<User> createUser(@Body User user);

    // DELETE a user by ID
    @DELETE("users/{id}")
    Call<User> deleteUser(@Path("id") long id);

    // PUT (update) a user by ID
    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") long id, @Body User user);
    @GET("questions")
    Call<List<question>> getquestions();


    // DELETE a user by ID


    // PUT (update) a user by ID


}


