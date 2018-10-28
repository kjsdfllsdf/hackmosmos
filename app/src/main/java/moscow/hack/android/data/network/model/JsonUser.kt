package moscow.hack.android.data.network.model

import com.google.gson.annotations.SerializedName

data class JsonUser(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)