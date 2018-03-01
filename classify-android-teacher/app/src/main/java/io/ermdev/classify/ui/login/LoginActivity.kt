package io.ermdev.classify.ui.login

import android.os.Bundle
import android.widget.EditText
import io.ermdev.classify.R
import io.ermdev.classify.data.remote.retrofit.service.TeacherService
import io.ermdev.classify.di.component.DaggerRetrofitComponent
import io.ermdev.classify.di.component.RetrofitComponent
import io.ermdev.classify.di.module.RetrofitModule
import io.ermdev.classify.ui.BasicActivity
import javax.inject.Inject

class LoginActivity : BasicActivity() {

    lateinit var retrofitComponent: RetrofitComponent

    @Inject
    lateinit var teacherService: TeacherService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(RetrofitModule("http://192.168.0.102:8080/api/"))
                .build()
        retrofitComponent.inject(this)

        val username: EditText = findViewById(R.id.username)
        username.setText(teacherService.getById(1).toString())
    }
}
