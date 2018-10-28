package moscow.hack.android.presentation.qr

import android.graphics.Bitmap
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface QrView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showQr(bitmap: Bitmap)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(text: CharSequence)
}
