package moscow.hack.android.data.network

import io.reactivex.Completable
import moscow.hack.android.data.network.model.JsonLogin
import moscow.hack.android.data.user.UserManager
import moscow.hack.android.domain.model.Event
import moscow.hack.android.domain.model.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    okHttpClient: OkHttpClient,
    private val userManager: UserManager
) {

    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://mlh-checkin.herokuapp.com")
            .validateEagerly(true)
            .build()
        api = retrofit.create(Api::class.java)
    }

    fun login(login: String, password: String, asCoach: Boolean): Completable = api.login(JsonLogin(login, password))
        .doOnSuccess {
            userManager.login(it.id, asCoach)
        }.ignoreElement()

    fun customerEvents(from: Long, to: Long) = api
        .customerEvents(userManager.getUserId()!!, from / 1000, to / 1000)
        .map {
            it.map {
                Event(it.id, it.name, it.coach, it.time * 1000, -1, it.registered == 1)
            }
        }

    fun trainerEvents(from: Long, to: Long) = api
        .trainerEvents(userManager.getUserId()!!, from / 1000, to / 1000)
        .map {
            it.map {
                val users = it.attendedCustomers + it.registeredCustomers
                Event(it.id, it.name, it.coach, it.time * 1000, users, it.registered == 1)
            }
        }

    fun register(eventId: String) = api.register(eventId, userManager.getUserId()!!)

    fun attend(eventId: String, userId: String) = api.attend(eventId, userManager.getUserId()!!, userId)

    fun check(eventId: String, userId: String) = api.check(userId, eventId)
        .map { User(it.id, it.name) }
}
