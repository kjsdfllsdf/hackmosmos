package moscow.hack.android.presentation.navigation

import android.support.v4.app.Fragment
import moscow.hack.android.presentation.event.EventFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

data class EventScreen(
    private val id: String
) : SupportAppScreen() {

    override fun getFragment(): Fragment = EventFragment.newInstance(id)
}
