package io.ermdev.classify.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.ermdev.classify.di.component.AppComponent
import io.ermdev.classify.di.component.DaggerAppComponent
import io.ermdev.classify.di.module.AppModule

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