package moscow.hack.android.presentation.login

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import moscow.hack.android.domain.login.LoginParams
import moscow.hack.android.domain.login.LoginUseCase
import moscow.hack.android.presentation.base.BasePresenter
import moscow.hack.android.presentation.navigation.CalendarScreen
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class LoginPresent @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val router: Router
) : BasePresenter<LoginView>() {

    fun login(login: String, password: String, asCoach: Boolean) {
        loginUseCase.run(LoginParams(login, password, asCoach))
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showLoading(true) }
            .subscribe({
                router.replaceScreen(CalendarScreen)
            }, {
                viewState.showLoading(false)
                viewState.showError(it.toString())
            })
            .untilDestroy()
    }
}
