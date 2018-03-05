package io.classify.di.component

import dagger.Component
import io.classify.di.module.RetrofitModule
import io.classify.ui.home.HomeScheduleFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface RetrofitComponent {

    fun inject(fragment: HomeScheduleFragment)
}