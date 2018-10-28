package moscow.hack.android.presentation.qr

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import moscow.hack.android.R
import moscow.hack.android.presentation.base.BaseFragment
import moscow.hack.android.presentation.util.ViewDelegate
import javax.inject.Inject
import javax.inject.Provider

class QrFragment : BaseFragment(), QrView {

    companion object {

        fun newInstance(): QrFragment = QrFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<QrPresenter>
    @InjectPresenter
    lateinit var presenter: QrPresenter

    private val qrView by ViewDelegate<ImageView>(R.id.qrView, viewDelegateReset)
    private val doneButton by ViewDelegate<View>(R.id.complete, viewDelegateReset)

    override val layoutId: Int = R.layout.qr

    @ProvidePresenter
    fun providePresenter() = presenterProvider.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doneButton.setOnClickListener { presenter.close() }
    }

    override fun showQr(bitmap: Bitmap) {
        qrView.setImageBitmap(bitmap)
    }

    override fun showError(text: CharSequence) {
        Toast.makeText(context!!, text, Toast.LENGTH_LONG).show()
    }
}
