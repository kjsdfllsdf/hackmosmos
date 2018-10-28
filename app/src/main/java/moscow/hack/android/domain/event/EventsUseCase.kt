package moscow.hack.android.domain.event

import io.reactivex.Single
import moscow.hack.android.data.network.NetworkRepository
import moscow.hack.android.domain.SingleUseCase
import moscow.hack.android.domain.model.Event
import javax.inject.Inject

class EventsUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) : SingleUseCase<EventsParams, List<Event>> {

    override fun run(param: EventsParams): Single<List<Event>> =
        networkRepository.customerEvents(param.from, param.to)
}
