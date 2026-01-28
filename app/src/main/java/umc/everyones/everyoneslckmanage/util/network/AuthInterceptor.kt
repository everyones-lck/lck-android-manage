package umc.everyones.everyoneslckmanage.util.network

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val spf: SharedPreferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        if (path.contains("/auth/login")) {
            return chain.proceed(request)
        }

        val authRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer ${spf.getString("jwt", "")}").build()
        return chain.proceed(authRequest)
    }
}