package io.classify.data.remote.service

import io.classify.data.model.Teacher
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TeacherService {

    @GET("teachers/{id}")
    fun getById(@Path("id") id: Long): Observable<Teacher>

    @GET("teachers")
    fun getAll(): Observable<List<Teacher>>
}