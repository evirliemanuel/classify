package io.classify.ui.home

import io.classify.data.model.Lesson
import io.classify.data.model.Schedule

interface HomeInteract {

    interface OnFinishedListener  {

        fun onGetLessons(lessons: List<Lesson>)

        fun onGetSchedules(schedules: List<Schedule>)
    }

    fun findSchedules();
}