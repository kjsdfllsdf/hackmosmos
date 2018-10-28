package moscow.hack.android.domain

import io.reactivex.Completable

interface CompletableUseCase<P> {

    fun run(param: P): Completable
}
