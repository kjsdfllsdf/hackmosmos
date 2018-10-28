package moscow.hack.android.data.user

import android.content.Context
import android.preference.PreferenceManager
import javax.inject.Inject

class UserManager @Inject constructor(
    context: Context
) {

    companion object {
        private const val USER_ID = "UserId"
        private const val COACH = "Coach"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getUserId(): String? = preferences.getString(USER_ID, null)

    fun isCoach(): Boolean = preferences.getBoolean(COACH, false)

    fun login(userId: String, coach: Boolean) {
        preferences.edit()
            .putString(USER_ID, userId)
            .putBoolean(COACH, coach)
            .apply()
    }

    fun logout() {
        preferences.edit()
            .remove(USER_ID)
            .remove(COACH)
            .apply()
    }
}