package moscow.hack.android.presentation.login

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import moscow.hack.android.R
import moscow.hack.android.presentation.base.BaseFragment
import moscow.hack.android.presentation.util.ViewDelegate
import javax.inject.Inject
import javax.inject.Provider

class LoginFragment : BaseFragment(), LoginView {

    companion object {

        fun newInstance() = LoginFragment()
    }

    @Inject
    lateinit var presenterProvider: Provider<LoginPresent>
    @InjectPresenter
    lateinit var presenter: LoginPresent

    private val loginEdit by ViewDelegate<EditText>(R.id.loginEdit, viewDelegateReset)
    private val passwordEdit by ViewDelegate<EditText>(R.id.passwordEdit, viewDelegateReset)
    private val loginButton by ViewDelegate<View>(R.id.loginButton, viewDelegateReset)
    private val coachCheck by ViewDelegate<CheckBox>(R.id.adminCheck, viewDelegateReset)

    private var loadingDialog: DialogInterface? = null

    override val layoutId: Int = R.layout.login

    @ProvidePresenter
    fun providePresenter() = presenterProvider.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener {
            presenter.login(loginEdit.text.toString(), passwordEdit.text.toString(), coachCheck.isChecked)
        }
    }

    override fun onStop() {
        super.onStop()
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    override fun showLoading(boolean: Boolean) {
        loadingDialog?.dismiss()
        if (boolean) {
            loadingDialog = ProgressDialog(context!!).apply { show() }
        }
    }

    override fun showError(text: CharSequence) {
        Toast.makeText(context!!, text.toString(), Toast.LENGTH_LONG).show()
    }
}
