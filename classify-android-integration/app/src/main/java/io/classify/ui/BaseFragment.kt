package io.classify.ui

import android.support.v4.app.Fragment
import io.classify.di.component.DaggerRetrofitComponent
import io.classify.di.component.RetrofitComponent
import io.classify.di.module.RetrofitModule

open class BaseFragment : Fragment() {

    var retrofitComponent: RetrofitComponent = DaggerRetrofitComponent
            .builder()
            .retrofitModule(RetrofitModule("http://192.168.0.104:8080/api/"))
            .build()
}