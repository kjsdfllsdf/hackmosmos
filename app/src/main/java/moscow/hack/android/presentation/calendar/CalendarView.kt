package moscow.hack.android.presentation.calendar

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import moscow.hack.android.domain.model.Event
import moscow.hack.android.presentation.calendar.model.CalendarItem

interface CalendarView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(loading: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showData(items: List<CalendarItem>, monthYear: Long)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEvents(items: List<Event>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(text: CharSequence)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRegisterButton(show: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun checkCamera(eventId: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showDialog(eventId: String)
}
