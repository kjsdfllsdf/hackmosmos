package moscow.hack.android.domain

import io.reactivex.Flowable

interface FlowableUseCase<P, R> {

    fun run(param: P): Flowable<R>
}
