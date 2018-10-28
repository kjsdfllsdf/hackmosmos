package moscow.hack.android.presentation.calendar.model

import moscow.hack.android.domain.model.Event

data class CalendarItem(
    val date: Long,
    val currentMonth: Boolean,
    val selected: Boolean,
    val events: List<Event>
)