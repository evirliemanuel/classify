package io.classify.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule(private val url: String) {

    @Singleton
    @Provides
    fun providesUrl() = url

    @Singleton
    @Provides
    fun providesBuilder(uri: String): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(uri)
    }

    @Singleton
    @Provides
    fun providesRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.build()
    }
}