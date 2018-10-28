package moscow.hack.android.domain.login

import io.reactivex.Completable
import moscow.hack.android.data.network.NetworkRepository
import moscow.hack.android.domain.CompletableUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) : CompletableUseCase<LoginParams> {

    override fun run(param: LoginParams): Completable =
        networkRepository.login(param.login, param.password, param.asCoach)
}
