package io.ermdev.classify.data.remote.retrofit.api

import io.ermdev.classify.data.model.Teacher
import io.ermdev.classify.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TeacherApi {

    @GET("teachers/{id}")
    fun getById(@Path("id" ) id: Long): Call<Teacher>

    @GET("teachers")
    fun getAll(): Call<List<Teacher>>

    @GET("teachers/{id}/user")
    fun getUser(@Path("id" ) id: Long): Call<User>
}