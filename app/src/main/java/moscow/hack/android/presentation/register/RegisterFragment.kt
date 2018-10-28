package moscow.hack.android.presentation.register

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.view.CameraView
import moscow.hack.android.R
import moscow.hack.android.presentation.base.BaseFragment
import moscow.hack.android.presentation.util.ViewDelegate
import javax.inject.Inject
import javax.inject.Provider

class RegisterFragment : BaseFragment(), RegisterView {

    companion object {

        private const val EVENT_ID = "EventId"

        fun newInstance(eventId: String): RegisterFragment = RegisterFragment().apply {
            arguments = Bundle().apply {
                putString(EVENT_ID, eventId)
            }
        }

        fun eventId(fragment: RegisterFragment): String = fragment.arguments!!.getString(EVENT_ID)!!
    }

    @Inject
    lateinit var presenterProvider: Provider<RegisterPresenter>
    @InjectPresenter
    lateinit var presenter: RegisterPresenter

    private val cameraView by ViewDelegate<CameraView>(R.id.cameraView, viewDelegateReset)
    private val nameView by ViewDelegate<TextView>(R.id.name, viewDelegateReset)
    private val registerButton by ViewDelegate<TextView>(R.id.register, viewDelegateReset)

    private lateinit var fotoapparat: Fotoapparat
    private var loading = false

    override val layoutId: Int = R.layout.register

    @ProvidePresenter
    fun providePresenter() = presenterProvider.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fotoapparat = Fotoapparat(
            context = context!!,
            view = cameraView,
            cameraConfiguration = CameraConfiguration(frameProcessor = {
                if (!loading) {
                    val reader = QRCodeReader()
                    val source = PlanarYUVLuminanceSource(it.image, it.size.width, it.size.height, 0, 0,
                        it.size.width, it.size.height, false)
                    try {
                        val result = reader.decode(BinaryBitmap(HybridBinarizer(source)))
                        if (result.barcodeFormat == BarcodeFormat.QR_CODE) {
                            presenter.check(result.text)
                        }
                    } catch (exception: Exception) {
                        // ignored
                    }
                }
            }))
    }

    override fun onStart() {
        super.onStart()
        fotoapparat.start()
    }

    override fun onStop() {
        super.onStop()
        fotoapparat.stop()
    }

    override fun showUserInfo(name: String?, info: String?, register: Boolean) {
        nameView.text = name
        registerButton.isEnabled = register
        when {
            name == null -> {
                registerButton.text = "Сканирование..."
                registerButton.setOnClickListener(null)
            }
            register -> {
                registerButton.text = "Зарегистрировать"
                registerButton.setOnClickListener { presenter.register() }
            }
            else -> {
                registerButton.text = "Нет абонемента!"
                registerButton.setOnClickListener(null)
            }
        }
    }

    override fun showLoading(loading: Boolean) {
        this.loading = loading
    }

    override fun showError(text: CharSequence) {
        Toast.makeText(context!!, text.toString(), Toast.LENGTH_LONG).show()
    }

    override fun showRegistered() {
        Toast.makeText(context!!, "Зарегистрирован!", Toast.LENGTH_LONG).show()
    }
}
