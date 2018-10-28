package moscow.hack.android.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Single
import moscow.hack.android.data.database.model.DbEvent

@Dao
interface EventDao {

    @Query("SELECT * FROM event WHERE time < :from AND time > :to")
    fun events(from: Long, to: Long): Flowable<List<DbEvent>>

    @Query("SELECT * FROM event WHERE id = :id")
    fun event(id: String): Single<DbEvent>

    @Insert
    fun insert(events: List<DbEvent>)

    @Query("DELETE FROM event WHERE time < :from AND time > :to")
    fun delete(from: Long, to: Long)

    @Transaction
    fun replace(events: List<DbEvent>, from: Long, to: Long) {
        delete(from, to)
        insert(events)
    }
}
