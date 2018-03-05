package io.classify.ui.home

import io.classify.data.model.Schedule

class FindScheduleInteractImpl : FindSchedulesInteract {

    override fun findSchedules(listener: FindSchedulesInteract.OnFinishedListener) {
        val list = ArrayList<Schedule>()
        list.add(Schedule(1, "Tuesday"))
        list.add(Schedule(2, "Monday"))
        list.add(Schedule(3, "Sunday"))
        listener.onFinished(list)
    }
}