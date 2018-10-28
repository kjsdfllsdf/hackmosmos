package moscow.hack.android.presentation.navigation

import moscow.hack.android.presentation.login.LoginFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object LoginScreen : SupportAppScreen() {

    override fun getFragment() = LoginFragment.newInstance()
}
