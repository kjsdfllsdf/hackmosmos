package moscow.hack.android.presentation.calendar.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import moscow.hack.android.R
import moscow.hack.android.domain.model.Event
import moscow.hack.android.presentation.util.ViewDelegate
import java.text.SimpleDateFormat

class EventAdapter : ListAdapter<Event, EventAdapter.EventViewHolder>(
    object : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(p0: Event, p1: Event): Boolean {
            return p0.id == p1.id
        }

        override fun areContentsTheSame(p0: Event, p1: Event): Boolean {
            return p0 == p1
        }
    }
) {

    companion object {
        private val timeFormatter = SimpleDateFormat("hh:mm")
    }

    var eventClick: ((Event) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_event, p0, false))
    }

    override fun onBindViewHolder(p0: EventViewHolder, p1: Int) {
        p0.bind(getItem(p1))
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val timeView by ViewDelegate<TextView>(R.id.time)
        private val nameView by ViewDelegate<TextView>(R.id.name)
        private val coachView by ViewDelegate<TextView>(R.id.coach)
        private val checkView by ViewDelegate<View>(R.id.checkView)

        fun bind(event: Event) {
            timeView.text = timeFormatter.format(event.time)
            nameView.text = event.name
            if (event.users >= 0) {
                coachView.text = "Записано на занятие: " + event.users
            } else {
                coachView.text = "Тренер: " + event.coach
            }
            checkView.visibility = if (event.registered) View.VISIBLE else View.INVISIBLE
            itemView.setOnClickListener { eventClick?.invoke(event) }
        }
    }
}
