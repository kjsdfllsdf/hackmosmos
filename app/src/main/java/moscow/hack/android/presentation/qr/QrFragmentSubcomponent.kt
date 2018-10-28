package moscow.hack.android.presentation.qr

import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface QrFragmentSubcomponent : AndroidInjector<QrFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<QrFragment>()
}
