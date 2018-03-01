package io.ermdev.classify.ui.login

import android.os.Bundle
import io.ermdev.classify.R
import io.ermdev.classify.di.component.DaggerRetrofitComponent
import io.ermdev.classify.di.component.RetrofitComponent
import io.ermdev.classify.di.module.RetrofitModule
import io.ermdev.classify.ui.BasicActivity
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
                .retrofitModule(RetrofitModule("http://localhost:8080/api"))
                .build()
    }
}