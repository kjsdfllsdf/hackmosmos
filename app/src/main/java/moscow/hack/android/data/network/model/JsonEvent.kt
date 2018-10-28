package moscow.hack.android.data.network.model

import com.google.gson.annotations.SerializedName

data class JsonEvent(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("trainer")
    val coach: String,
    @SerializedName("date")
    val time: Long,
    @SerializedName("attended_customers")
    val attendedCustomers: Long,
    @SerializedName("registered_customers")
    val registeredCustomers: Long,
    @SerializedName("is_registered")
    val registered: Int
)