package moscow.hack.android.presentation.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import moscow.hack.android.presentation.util.ViewDelegateReset
import javax.inject.Inject

/**
 * MvpAppCompatFragment + DaggerFragment.
 */
abstract class BaseFragment : MvpAppCompatFragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    @get:LayoutRes
    protected open val layoutId: Int
        get() {
            throw NotImplementedError("Override this or onCreateView")
        }

    protected val viewDelegateReset = ViewDelegateReset()

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutId, container, false)

    override fun onDestroyView() {
        super.onDestroyView()
        viewDelegateReset.onDestroyView()
    }
}
