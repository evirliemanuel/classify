package io.classify.service;

import com.remswork.project.alice.model.UserDetail;

import io.classify.model.User;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @GET("userDetail")
    Observable<User> getByUsername(@Query("username") String username);

    @PUT("userDetail/{userId}")
    Observable<User> update(@Path("userId") long userId, @Body UserDetail userDetail);
}
