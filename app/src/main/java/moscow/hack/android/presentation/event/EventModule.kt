package moscow.hack.android.presentation.event

import dagger.Module
import dagger.Provides

@Module
class EventModule {

    @Provides
    fun id(fragment: EventFragment): String = EventFragment.id(fragment)
}
