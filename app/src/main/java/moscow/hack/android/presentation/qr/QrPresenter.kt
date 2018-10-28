package moscow.hack.android.presentation.qr

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import moscow.hack.android.domain.qr.GenerateQrBitmapUseCase
import moscow.hack.android.presentation.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class QrPresenter @Inject constructor(
    private val generateQrBitmapUseCase: GenerateQrBitmapUseCase,
    private val router: Router
) : BasePresenter<QrView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        generateQrBitmapUseCase.run(Unit)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showQr(it)
            }, {
                viewState.showError(it.toString())
            })
            .untilDestroy()
    }

    fun close() {
        router.exit()
    }
}
