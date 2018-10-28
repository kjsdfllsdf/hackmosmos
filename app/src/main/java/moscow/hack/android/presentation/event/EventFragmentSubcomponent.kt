package moscow.hack.android.presentation.event

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [EventModule::class])
interface EventFragmentSubcomponent : AndroidInjector<EventFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<EventFragment>()
}
