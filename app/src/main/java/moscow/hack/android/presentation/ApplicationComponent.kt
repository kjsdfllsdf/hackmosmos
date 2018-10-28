package moscow.hack.android.presentation

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import moscow.hack.android.data.DataModule
import moscow.hack.android.domain.DomainModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilder::class,
    ApplicationModule::class,
    DomainModule::class,
    DataModule::class
])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
