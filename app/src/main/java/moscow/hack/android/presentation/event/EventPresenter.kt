package moscow.hack.android.presentation.event

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import moscow.hack.android.domain.event.EventUseCase
import moscow.hack.android.domain.event.RegisterEventUseCase
import moscow.hack.android.presentation.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class EventPresenter @Inject constructor(
    private val eventId: String,
    private val eventUseCase: EventUseCase,
    private val registerEventUseCase: RegisterEventUseCase,
    private val router: Router
) : BasePresenter<EventView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        eventUseCase.run(eventId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {})
            .untilDestroy()
    }

    fun register() {
        registerEventUseCase.run(eventId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {})
            .untilDestroy()
    }
}
