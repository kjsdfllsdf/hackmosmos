package moscow.hack.android.data

import dagger.Module
import moscow.hack.android.data.database.DatabaseModule
import moscow.hack.android.data.network.NetworkModule

@Module(includes = [NetworkModule::class, DatabaseModule::class])
class DataModule
