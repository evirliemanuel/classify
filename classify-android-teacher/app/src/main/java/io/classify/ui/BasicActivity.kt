package io.classify.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.classify.di.component.AppComponent
import io.classify.di.component.DaggerAppComponent
import io.classify.di.module.AppModule

open class BasicActivity: AppCompatActivity() {

    lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(application))
                .build()
    }
}