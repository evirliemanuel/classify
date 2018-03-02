package io.classify.ui.login

import android.os.Bundle
import android.widget.EditText
import io.ermdev.classify.R
import io.classify.data.remote.retrofit.api.TeacherApi
import io.classify.di.component.DaggerRetrofitComponent
import io.classify.di.component.RetrofitComponent
import io.classify.di.module.RetrofitModule
import io.classify.ui.BasicActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginActivity : BasicActivity() {

    lateinit var retrofitComponent: RetrofitComponent

    @Inject
    lateinit var api: TeacherApi

    lateinit var username: EditText

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(RetrofitModule("http://192.168.0.104:8080/api/"))
                .build()
        retrofitComponent.inject(this)

        username = findViewById(R.id.username)

        login()
    }

    fun login() {
        disposable = api.getById(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result -> username.setText(result.toString()) })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null) {
            disposable?.dispose();
        }
    }
}
