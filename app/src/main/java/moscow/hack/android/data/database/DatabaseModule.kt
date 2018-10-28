package moscow.hack.android.data.database

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun appDatabase(context: Context) =
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "database")
            .build()
}
