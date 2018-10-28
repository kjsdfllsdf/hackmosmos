package moscow.hack.android.presentation.calendar

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.BiFunction
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers
import moscow.hack.android.data.network.NetworkRepository
import moscow.hack.android.data.user.UserManager
import moscow.hack.android.domain.model.Event
import moscow.hack.android.presentation.base.BasePresenter
import moscow.hack.android.presentation.calendar.model.CalendarItem
import moscow.hack.android.presentation.navigation.QrScreen
import moscow.hack.android.presentation.navigation.RegisterScreen
import ru.terrakok.cicerone.Router
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class CalendarPresenter @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val userManager: UserManager,
    private val router: Router
) : BasePresenter<CalendarView>() {

    companion object {
        private val EMPTY_CALENDAR = CalendarItem(0, false, false, emptyList())
    }

    private val calendar = Calendar.getInstance()
    private val selectedDayProcessor = BehaviorProcessor.createDefault(EMPTY_CALENDAR)

    private var eventsDisposable: Disposable = Disposables.empty()
    private var lastInterval: Pair<Long, Long> = Pair(0, 0)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        observeSelected()
        viewState.showRegisterButton(!userManager.isCoach())
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val start = calendar.timeInMillis
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val end = calendar.timeInMillis
        setInterval(start, end)
    }

    private fun observeSelected() {
        selectedDayProcessor
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                viewState.showEvents(it.events)
            }
            .untilDestroy()
    }

    fun setInterval(from: Long, to: Long) {
        lastInterval = Pair(from, to)
        eventsDisposable.dispose()
        val lastCalendar = selectedDayProcessor.value!!
        if (lastCalendar.date < from || lastCalendar.date > to) {
            selectedDayProcessor.offer(EMPTY_CALENDAR)
        }
        eventsDisposable = Flowable.combineLatest(
            (if (userManager.isCoach())
                networkRepository.trainerEvents(from, to)
            else
                networkRepository.customerEvents(from, to))
                .toFlowable()
                .delay(500, TimeUnit.MILLISECONDS)
                .startWith(emptyList<Event>())
                .doOnError { it.printStackTrace() }
                .onErrorResumeNext(Flowable.empty<List<Event>>()),
            selectedDayProcessor,
            BiFunction<List<Event>, CalendarItem, Pair<List<Event>, CalendarItem>> { t1, t2 -> Pair(t1, t2) })
            .observeOn(Schedulers.computation())
            .map {
                val calendar = Calendar.getInstance()
                calendar.time = Date(from)
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                val startTime = calendar.timeInMillis
                val month = calendar.get(Calendar.MONTH)
                val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                val minusDays = when (dayOfWeek) {
                    Calendar.MONDAY -> 0
                    Calendar.TUESDAY -> 1
                    Calendar.WEDNESDAY -> 2
                    Calendar.THURSDAY -> 3
                    Calendar.FRIDAY -> 4
                    Calendar.SATURDAY -> 5
                    Calendar.SUNDAY -> 6
                    else -> throw IllegalArgumentException("Day of week")
                }
                calendar.add(Calendar.DAY_OF_YEAR, -minusDays)
                val list = mutableListOf<CalendarItem>()
                while (list.size < 6 * 7) {
                    val millis = calendar.timeInMillis
                    list += CalendarItem(
                        millis,
                        calendar.get(Calendar.MONTH) == month,
                        millis == it.second.date,
                        it.first.filter { it.time >= millis && it.time <= millis + TimeUnit.DAYS.toMillis(1) })
                    calendar.add(Calendar.DAY_OF_YEAR, 1)
                }
                Pair(list, startTime)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showData(it.first, it.second)
                val day = selectedDayProcessor.value!!
                it.first.forEach {
                    if (it.selected && it.date == day.date && it != day) {
                        selectedDayProcessor.offer(it)
                    }
                }
            }, {
                viewState.showError(it.toString())
            })
            .untilDestroy()
    }

    fun selectDay(calendarItem: CalendarItem) {
        if (calendarItem.currentMonth && calendarItem.events.isNotEmpty()) {
            selectedDayProcessor.offer(calendarItem)
        }
    }

    fun nextMonth() {
        changeInterval(1)
    }

    fun prevMonth() {
        changeInterval(-1)
    }

    private fun changeInterval(delta: Int) {
        calendar.time = Date(lastInterval.first)
        calendar.add(Calendar.MONTH, delta)
        val start = calendar.timeInMillis
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val end = calendar.timeInMillis
        setInterval(start, end)
    }

    fun onEventClick(event: Event) {
        if (userManager.isCoach()) {
            viewState.checkCamera(event.id)
        } else {
            viewState.showDialog(event.id)
        }
    }

    fun attend() {
        router.navigateTo(QrScreen)
    }

    fun onCameraPermissionGranted(eventId: String) {
        if (userManager.isCoach()) {
            router.navigateTo(RegisterScreen(eventId))
        }
    }

    fun register(eventId: String) {
        networkRepository.register(eventId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading(true) }
            .subscribe({
                setInterval(lastInterval.first, lastInterval.second)
                viewState.showLoading(false)
            }, {
                viewState.showError(it.toString())
                viewState.showLoading(false)
            })
            .untilDestroy()
    }
}
