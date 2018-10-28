package moscow.hack.android.data.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "event")
open class DbEvent(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "time")
    val time: Long,
    @ColumnInfo(name = "duration")
    val duration: Long
)