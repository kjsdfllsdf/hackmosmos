package moscow.hack.android.presentation.navigation

import android.support.v4.app.Fragment
import moscow.hack.android.presentation.calendar.CalendarFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object CalendarScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = CalendarFragment.newInstance()
}
