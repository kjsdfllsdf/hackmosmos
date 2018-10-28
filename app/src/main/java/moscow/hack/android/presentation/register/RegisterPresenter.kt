package moscow.hack.android.presentation.register

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import moscow.hack.android.data.network.NetworkRepository
import moscow.hack.android.presentation.base.BasePresenter
import retrofit2.HttpException
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class RegisterPresenter @Inject constructor(
    private val eventId: String,
    private val router: Router,
    private val networkRepository: NetworkRepository
) : BasePresenter<RegisterView>() {

    private var lastUserId: String? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showUserInfo(null, null, false)
    }

    fun check(userId: String) {
        if (lastUserId == userId) {
            return
        }
        lastUserId = userId
        networkRepository.check(eventId, userId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading(true) }
            .subscribe({
                viewState.showUserInfo(it.name, null, true)
                viewState.showLoading(false)
            }, {
                if (it is HttpException) {
                    viewState.showUserInfo("", null, false)
                } else {
                    viewState.showUserInfo(null, null, false)
                }
                viewState.showLoading(false)
                viewState.showError(it.toString())
            })
            .untilDestroy()
    }

    fun register() {
        val userId = lastUserId ?: return
        networkRepository.attend(eventId, userId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading(true) }
            .subscribe({
                viewState.showRegistered()
                viewState.showUserInfo(null, null, false)
                viewState.showLoading(false)
            }, {
                viewState.showLoading(false)
                viewState.showError(it.toString())
            })
            .untilDestroy()
    }

    fun exit() {
        router.exit()
    }
}
