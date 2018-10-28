package moscow.hack.android.presentation.calendar.adapter

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import moscow.hack.android.R
import moscow.hack.android.presentation.calendar.model.CalendarItem
import moscow.hack.android.presentation.util.ViewDelegate
import java.text.SimpleDateFormat

class CalendarItemAdapter : ListAdapter<CalendarItem, CalendarItemAdapter.CalendarViewHolder>(
    object : DiffUtil.ItemCallback<CalendarItem>() {

        override fun areItemsTheSame(p0: CalendarItem, p1: CalendarItem): Boolean {
            return p0.date == p1.date
        }

        override fun areContentsTheSame(p0: CalendarItem, p1: CalendarItem): Boolean {
            return p0 == p1
        }
    }
) {

    companion object {
        private val formatter = SimpleDateFormat("d")
    }

    var calendarClick: ((CalendarItem) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CalendarViewHolder {
        return CalendarViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_calendar, p0, false))
    }

    override fun onBindViewHolder(p0: CalendarViewHolder, p1: Int) {
        p0.bind(getItem(p1))
    }

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView by ViewDelegate<TextView>(R.id.text)
        private val iconView by ViewDelegate<View>(R.id.icon)

        fun bind(calendarItem: CalendarItem) {
            textView.text = formatter.format(calendarItem.date)
            textView.isEnabled = calendarItem.currentMonth
            textView.isActivated = calendarItem.selected
            iconView.visibility = if (calendarItem.events.isEmpty()) View.INVISIBLE else View.VISIBLE
            itemView.setOnClickListener { calendarClick?.invoke(calendarItem) }
        }
    }
}
