package io.classify.data.remote.service

import io.classify.data.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("users/{id}")
    fun getById(@Path("id") id: Long): Observable<User>

    @GET("users")
    fun getByUsername(@Query("username") username: String): Observable<User>

    @GET("users")
    fun getAll(): Observable<List<User>>
}