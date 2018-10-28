package moscow.hack.android.presentation.calendar

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface CalendarFragmentSubcomponent : AndroidInjector<CalendarFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<CalendarFragment>()
}
