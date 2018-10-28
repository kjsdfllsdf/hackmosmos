package moscow.hack.android.data.database

import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    appDatabase: AppDatabase
) {

    private val eventDao: EventDao = appDatabase.eventDao()

    /*fun events(from: Long, to: Long) = eventDao.events(from, to)
        .map {
            it.map { Event(it.id, it.name, "", it.time) }
        }

    fun event(id: String) = eventDao.event(id)
        .map { Event(it.id, it.name, "", it.time) }

    fun replace(events: List<Event>, from: Long, to: Long): Completable =
        Completable.fromAction {
            val list = events.map { DbEvent(it.id, it.name, it.time, 0) }
            eventDao.replace(list, from, to)
        }*/
}
