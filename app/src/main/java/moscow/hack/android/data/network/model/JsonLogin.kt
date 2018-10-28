package moscow.hack.android.data.network.model

import com.google.gson.annotations.SerializedName

data class JsonLogin(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String
)
