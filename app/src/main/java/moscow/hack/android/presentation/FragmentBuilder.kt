package moscow.hack.android.presentation

import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import moscow.hack.android.presentation.calendar.CalendarFragment
import moscow.hack.android.presentation.calendar.CalendarFragmentSubcomponent
import moscow.hack.android.presentation.event.EventFragment
import moscow.hack.android.presentation.event.EventFragmentSubcomponent
import moscow.hack.android.presentation.login.LoginFragment
import moscow.hack.android.presentation.login.LoginFragmentSubcomponent
import moscow.hack.android.presentation.qr.QrFragment
import moscow.hack.android.presentation.qr.QrFragmentSubcomponent
import moscow.hack.android.presentation.register.RegisterFragment
import moscow.hack.android.presentation.register.RegisterFragmentSubcomponent

@Module(subcomponents = [
    LoginFragmentSubcomponent::class,
    CalendarFragmentSubcomponent::class,
    EventFragmentSubcomponent::class,
    QrFragmentSubcomponent::class,
    RegisterFragmentSubcomponent::class
])
abstract class FragmentBuilder {

    @Binds
    @IntoMap
    @FragmentKey(LoginFragment::class)
    abstract fun bindLoginFragment(builder: LoginFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(CalendarFragment::class)
    abstract fun bindCalendarFragment(builder: CalendarFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(EventFragment::class)
    abstract fun bindEventFragment(builder: EventFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(QrFragment::class)
    abstract fun bindQrFragment(builder: QrFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(RegisterFragment::class)
    abstract fun bindRegisterFragment(builder: RegisterFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>

}
