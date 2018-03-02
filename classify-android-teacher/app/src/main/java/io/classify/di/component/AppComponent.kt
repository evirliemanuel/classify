package io.classify.di.component

import dagger.Component
import io.classify.MainActivity
import io.classify.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
}