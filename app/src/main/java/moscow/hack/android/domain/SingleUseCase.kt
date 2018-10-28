package moscow.hack.android.domain

import io.reactivex.Single

interface SingleUseCase<P, R> {

    fun run(param: P): Single<R>
}
