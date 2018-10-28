package moscow.hack.android.presentation.register

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface RegisterView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showUserInfo(name: String?, info: String?, register: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(loading: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(text: CharSequence)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showRegistered()
}
