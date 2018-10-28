package moscow.hack.android.presentation.login

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface LoginView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(boolean: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(text: CharSequence)
}
