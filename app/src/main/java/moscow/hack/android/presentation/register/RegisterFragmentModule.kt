package moscow.hack.android.presentation.register

import dagger.Module
import dagger.Provides

@Module
class RegisterFragmentModule {

    @Provides
    fun eventId(fragment: RegisterFragment): String = RegisterFragment.eventId(fragment)
}
