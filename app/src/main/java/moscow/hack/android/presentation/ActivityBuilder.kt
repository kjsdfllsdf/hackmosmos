package moscow.hack.android.presentation

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import moscow.hack.android.presentation.activity.MainActivity
import moscow.hack.android.presentation.activity.MainActivitySubcomponent

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class ActivityBuilder {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun bindMainActivity(builder: MainActivitySubcomponent.Builder): AndroidInjector.Factory<out Activity>
}
