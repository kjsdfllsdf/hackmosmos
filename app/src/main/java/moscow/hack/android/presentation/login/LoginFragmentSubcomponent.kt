package moscow.hack.android.presentation.login

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface LoginFragmentSubcomponent : AndroidInjector<LoginFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<LoginFragment>()
}
