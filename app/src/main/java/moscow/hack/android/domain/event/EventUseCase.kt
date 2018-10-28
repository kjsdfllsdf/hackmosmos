package moscow.hack.android.domain.event

import io.reactivex.Flowable
import moscow.hack.android.domain.FlowableUseCase
import moscow.hack.android.domain.model.Event
import javax.inject.Inject

class EventUseCase @Inject constructor() : FlowableUseCase<String, Event> {

    override fun run(param: String): Flowable<Event> {
        return Flowable.empty()
    }
}
