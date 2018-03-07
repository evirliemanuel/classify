package io.classify;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DI {

    final String url = "http:192.168.0.102:8080/api/";

    final Retrofit retrofit;

    public DI() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(url)
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
