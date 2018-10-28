package moscow.hack.android.presentation.event

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import moscow.hack.android.R
import moscow.hack.android.presentation.base.BaseFragment
import moscow.hack.android.presentation.util.ViewDelegate
import javax.inject.Inject
import javax.inject.Provider

class EventFragment : BaseFragment(), EventView {

    companion object {

        private const val ID = "Id"

        fun newInstance(id: String): EventFragment = EventFragment().apply {
            arguments = Bundle().apply {
                putString(ID, id)
            }
        }

        fun id(fragment: EventFragment): String = fragment.arguments!!.getString(ID)!!
    }

    @Inject
    lateinit var presenterProvider: Provider<EventPresenter>
    @InjectPresenter
    lateinit var presenter: EventPresenter

    private val textView by ViewDelegate<View>(R.id.text, viewDelegateReset)

    override val layoutId: Int = R.layout.hello_world

    @ProvidePresenter
    fun providePresenter() = presenterProvider.get()
}
