package io.ermdev.classify.di.component

import dagger.Component
import io.ermdev.classify.MainActivity
import io.ermdev.classify.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
}