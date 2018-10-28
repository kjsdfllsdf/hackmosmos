package moscow.hack.android.data.network

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
class NetworkModule {

    @Provides
    fun okHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
        return builder.build()
    }
}
