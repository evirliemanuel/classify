package io.ermdev.classify.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule(private val uri: String) {

    @Singleton
    @Provides
    fun providesUri(): String = uri

    @Singleton
    @Provides
    fun providesBuilder(uri: String): Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl(uri)
                .addConverterFactory(GsonConverterFactory.create());
    }

    @Singleton
    @Provides
    fun providesRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.build()
    }
}