package moscow.hack.android.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import moscow.hack.android.data.database.model.DbEvent

@Database(entities = [DbEvent::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
}
