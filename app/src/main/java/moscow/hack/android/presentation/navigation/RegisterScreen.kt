package moscow.hack.android.presentation.navigation

import android.support.v4.app.Fragment
import moscow.hack.android.presentation.register.RegisterFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

data class RegisterScreen(
    private val eventId: String
) : SupportAppScreen() {

    override fun getFragment(): Fragment = RegisterFragment.newInstance(eventId)
}
