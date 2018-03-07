package io.classify.service;

import io.classify.model.User;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {

    @GET("userDetail")
    Observable<User> getByUsername(@Query("username") String username);
}
