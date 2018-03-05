package io.classify.ui.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.classify.R
import io.classify.data.model.Schedule
import io.classify.ui.BaseFragment
import retrofit2.Retrofit
import javax.inject.Inject

class HomeScheduleFragment : BaseFragment(), HomeView {

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setSchedules(schedules: List<Schedule>) {
        adapter = HomeScheduleAdapter(context, schedules)
        val l = LinearLayoutManager(context)
        l.orientation = LinearLayoutManager.VERTICAL
        rv?.adapter = adapter
        rv?.layoutManager = l
    }

    @Inject
    lateinit var retrofit: Retrofit

    var rootView: View? = null

    var adapter: HomeScheduleAdapter? = null

    var rv: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_home_schedule, container, false)
        rv = rootView?.findViewById(R.id.rv_schedule)
        val interact = HomeScheduleInteractImpl()
        val presenter = HomePresenterImpl(this, interact)
        presenter.showSchedules()
        return rootView
    }
}