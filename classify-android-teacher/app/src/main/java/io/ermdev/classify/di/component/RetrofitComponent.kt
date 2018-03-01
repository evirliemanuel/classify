package io.ermdev.classify.di.component

import dagger.Component
import io.ermdev.classify.di.module.RetrofitModule
import io.ermdev.classify.ui.login.LoginActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface RetrofitComponent {

    fun inject(activity: LoginActivity)
}