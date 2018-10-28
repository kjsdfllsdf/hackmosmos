package moscow.hack.android.presentation.register

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [RegisterFragmentModule::class])
interface RegisterFragmentSubcomponent : AndroidInjector<RegisterFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<RegisterFragment>()
}
