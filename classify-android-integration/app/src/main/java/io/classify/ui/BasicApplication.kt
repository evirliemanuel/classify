package io.classify.ui

import android.app.Application
import io.classify.di.component.AppComponent
import io.classify.di.component.DaggerAppComponent
import io.classify.di.module.AppModule
import io.classify.di.module.RetrofitModule

open class BasicApplication : Application() {

    var appComponent: AppComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .retrofitModule(RetrofitModule("http://192.168.0.104:8080/api/"))
            .build()

}