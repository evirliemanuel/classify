package io.classify.ui.home

import android.util.Log
import io.classify.data.model.Schedule

class HomePresenterImpl(val homeView: HomeView, val findSchedulesInteract: FindSchedulesInteract)
    : HomePresenter, FindSchedulesInteract.OnFinishedListener {

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
        homeView.hideProgress()
        homeView.setSchedules(schedules)
    }

}