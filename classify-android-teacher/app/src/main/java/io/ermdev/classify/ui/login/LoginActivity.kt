package io.ermdev.classify.ui.login

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import io.ermdev.classify.R
import io.ermdev.classify.data.model.Teacher
import io.ermdev.classify.data.remote.retrofit.TeacherService
import io.ermdev.classify.di.component.DaggerRetrofitComponent
import io.ermdev.classify.di.component.RetrofitComponent
import io.ermdev.classify.di.module.RetrofitModule
import io.ermdev.classify.ui.BasicActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class LoginActivity : BasicActivity() {

    lateinit var retrofitComponent: RetrofitComponent

    @Inject
    lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(RetrofitModule("http://192.168.0.102:8080/api/"))
                .build()

        retrofitComponent.inject(this)

        val teacherService = retrofit.create(TeacherService::class.java)

        val username: EditText = findViewById(R.id.username)

        val call: Call<Teacher> = teacherService.getById(1)

        call.enqueue(object : Callback<Teacher> {
            override fun onResponse(call: Call<Teacher>?, response: Response<Teacher>?) {
                var succ = response?.isSuccessful()
                var teacher = response?.body()

                Log.i("retrofit", "${response}")
                Log.i("retrofit", "success : ${succ}")

                Log.i("retrofit", "success : ${teacher}")
            }

            override fun onFailure(call: Call<Teacher>?, t: Throwable?) {
                Log.i("retrofit", "error")
            }
        })
    }
}
