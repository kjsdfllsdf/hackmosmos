package moscow.hack.android.presentation.activity

import android.os.Bundle
import moscow.hack.android.data.user.UserManager
import moscow.hack.android.presentation.base.BaseActivity
import moscow.hack.android.presentation.navigation.CalendarScreen
import moscow.hack.android.presentation.navigation.LoginScreen
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var userManager: UserManager

    private lateinit var navigator: SupportAppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator = SupportAppNavigator(this, android.R.id.content)
        if (savedInstanceState == null) {
            if (userManager.getUserId() == null) {
                router.replaceScreen(LoginScreen)
            } else {
                router.replaceScreen(CalendarScreen)
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}
