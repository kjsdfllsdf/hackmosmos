package moscow.hack.android.presentation

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins

class HackApplication : DaggerApplication() {

    private val applicationComponent by lazy { DaggerApplicationComponent.builder().application(this).build() }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationComponent

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { }
    }
}
