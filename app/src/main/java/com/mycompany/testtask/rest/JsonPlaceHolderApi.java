package com.mycompany.testtask.rest;

import com.mycompany.testtask.pojo.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("users")
    Observable<List<User>> getUsers();
}
