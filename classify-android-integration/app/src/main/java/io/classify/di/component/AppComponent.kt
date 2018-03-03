package io.classify.di.component

import dagger.Component
import io.classify.di.module.AppModule
import io.classify.di.module.RetrofitModule
import io.classify.ui.login.LoginActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class])
interface AppComponent {

    fun inject(activity: LoginActivity)
}