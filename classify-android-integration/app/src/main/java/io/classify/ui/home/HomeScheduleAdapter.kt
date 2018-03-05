package io.classify.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.classify.R
import io.classify.data.model.Schedule
import kotlinx.android.synthetic.main.row_schedule.view.*

class HomeScheduleAdapter(context: Context, private var schedules: List<Schedule>)
    : RecyclerView.Adapter<HomeScheduleAdapter.HomeScheduleViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HomeScheduleViewHolder {
        val rootView = inflater.inflate(R.layout.row_schedule, parent, false)
        return HomeScheduleViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return schedules.size
    }

    override fun onBindViewHolder(holder: HomeScheduleViewHolder?, position: Int) {
        holder?.setView(schedule = schedules[position])
    }

    class HomeScheduleViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun setView(schedule: Schedule) {
            Log.i("ScheduleViewHolder", schedule.day)
            itemView.title.text = schedule.day
        }
    }
}