package info.imdang.data.interceptor

import info.imdang.data.constant.Header
import info.imdang.data.datasource.lcoal.AuthLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val authLocalDataSource: AuthLocalDataSource) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = authLocalDataSource.getAccessToken()

        return chain.proceed(
            chain
                .request()
                .newBuilder()
                .addHeader(Header.AUTHORIZATION, "Bearer $accessToken")
                .url(chain.request().url)
                .build()
        )
    }
}
