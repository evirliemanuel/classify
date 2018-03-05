package io.classify.ui.home

import android.util.Log
import io.classify.data.model.Schedule

class HomePresenterImpl(val homeView: HomeView, val homeSchedulesInteract: HomeSchedulesInteract)
    : HomePresenter, HomeSchedulesInteract.OnFinishedListener {

    override fun showSchedules() {
        homeSchedulesInteract.findSchedules(this)
    }

    override fun onResume() {
        homeView.showProgress()
    }

    override fun onScheduleClicked(position: Int) {
        Log.i("HomeView", "Position ${position} clicked")
    }

    override fun onDestroy() {
        homeView.hideProgress()
    }

    override fun onFinished(schedules: List<Schedule>) {
        homeView.setSchedules(schedules)
    }

}