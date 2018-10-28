package moscow.hack.android.domain.event

import io.reactivex.Completable
import moscow.hack.android.domain.CompletableUseCase
import javax.inject.Inject

class RegisterEventUseCase @Inject constructor() : CompletableUseCase<String> {

    override fun run(param: String): Completable {
        return Completable.complete()
    }
}
