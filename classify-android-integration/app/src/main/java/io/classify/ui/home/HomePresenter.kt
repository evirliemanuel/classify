package io.classify.ui.home

interface HomePresenter {

    fun onResume()

    fun onScheduleClicked(position: Int);

    fun onDestroy()

    fun showSchedules()
}
