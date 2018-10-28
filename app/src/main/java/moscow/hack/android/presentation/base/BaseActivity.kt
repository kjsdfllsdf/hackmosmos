package moscow.hack.android.presentation.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector
}
