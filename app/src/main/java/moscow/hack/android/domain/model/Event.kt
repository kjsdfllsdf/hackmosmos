package moscow.hack.android.domain.model

data class Event(
    val id: String,
    val name: String,
    val coach: String,
    val time: Long,
    val users: Long,
    val registered: Boolean
)