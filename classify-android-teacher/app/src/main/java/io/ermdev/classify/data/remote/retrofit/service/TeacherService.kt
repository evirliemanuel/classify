package io.ermdev.classify.data.remote.retrofit.service

import io.ermdev.classify.data.model.Teacher
import io.ermdev.classify.data.remote.retrofit.api.TeacherApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherService(val teacherApi: TeacherApi) {

    fun getById(id: Long): Teacher? {
        val call: Call<Teacher> = teacherApi.getById(id)
        var teacher: Teacher? = null

        call.enqueue(object : Callback<Teacher> {
            override fun onResponse(call: Call<Teacher>?, response: Response<Teacher>?) {
                teacher = response?.body()
            }

            override fun onFailure(call: Call<Teacher>?, t: Throwable?) {
                teacher = null
            }
        })
        return teacher
    }
}