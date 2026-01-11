package umc.everyones.everyoneslckmanage.util.network

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val spf: SharedPreferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer ${spf.getString("jwt", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJyb2xlIjoiUk9MRV9BRE1JTiIsImlhdCI6MTc2Nzk3Mjg0NCwiZXhwIjoxNzY5Nzg3MjQ0fQ.t_sQmtVKpkwJmv6q9l9pdbY98XbbR46CpwHlVsVWeeA")}").build()
        return chain.proceed(authRequest)
    }
}