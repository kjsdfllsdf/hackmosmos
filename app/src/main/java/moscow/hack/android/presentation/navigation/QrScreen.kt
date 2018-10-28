package moscow.hack.android.presentation.navigation

import android.support.v4.app.Fragment
import moscow.hack.android.presentation.qr.QrFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object QrScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = QrFragment.newInstance()
}
