package io.classify.ui.home

import io.classify.data.model.Schedule

interface HomeView {

    fun showProgress()

    fun hideProgress()

    fun setSchedules(schedules: List<Schedule>)
}