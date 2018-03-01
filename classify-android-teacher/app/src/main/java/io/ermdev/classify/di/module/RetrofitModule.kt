package io.ermdev.classify.di.module

import dagger.Module
import dagger.Provides
import io.ermdev.classify.data.remote.retrofit.api.TeacherApi
import io.ermdev.classify.data.remote.retrofit.service.TeacherService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule(private val uri: String) {

    @Provides
    @Singleton
    fun providesUri(): String = uri

    @Provides
    @Singleton
    fun providesBuilder(uri: String): Retrofit.Builder {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(uri)
    }

    @Provides
    @Singleton
    fun providesRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.build()
    }

    @Provides
    @Singleton
    fun providesTeacherApi(retrofit: Retrofit): TeacherApi {
        return retrofit.create(TeacherApi::class.java)
    }

    @Provides
    @Singleton
    fun providesTeacherService(teacherApi: TeacherApi): TeacherService {
        return TeacherService(teacherApi)
    }
}