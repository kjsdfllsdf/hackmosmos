package moscow.hack.android.data.network

import io.reactivex.Completable
import io.reactivex.Single
import moscow.hack.android.data.network.model.JsonEvent
import moscow.hack.android.data.network.model.JsonLogin
import moscow.hack.android.data.network.model.JsonUser
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @POST("/api/login")
    fun login(@Body login: JsonLogin): Single<JsonUser>

    @GET("/api/customer/events")
    fun customerEvents(
        @Query("user_id") userId: String,
        @Query("start_date") from: Long,
        @Query("finish_date") to: Long
    ): Single<List<JsonEvent>>

    @GET("/api/trainer/events")
    fun trainerEvents(
        @Query("user_id") userId: String,
        @Query("start_date") from: Long,
        @Query("finish_date") to: Long
    ): Single<List<JsonEvent>>

    @POST("/api/events/{eventId}/register")
    fun register(@Path("eventId") eventId: String, @Query("user_id") userId: String): Completable

    @POST("/api/events/{eventId}/attend")
    fun attend(
        @Path("eventId") eventId: String,
        @Query("user_id") userId: String,
        @Query("customer_id") customerId: String
    ): Completable

    @GET("/api/events/{eventId}/check/{userId}")
    fun check(
        @Path("userId") userId: String,
        @Path("eventId") eventId: String
    ): Single<JsonUser>
}
