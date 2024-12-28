package info.imdang.remote.service

import info.imdang.data.model.response.google.GoogleAccessTokenResponse
import info.imdang.remote.BuildConfig
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface GoogleService {

    /** 구글 AccessToken 조회 */
    @FormUrlEncoded
    @POST("token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("client_id") clientId: String = BuildConfig.GOOGLE_WEB_CLIENT_ID,
        @Field("client_secret") clientSecret: String = BuildConfig.GOOGLE_WEB_CLIENT_SECRET,
        @Field("code") code: String
    ): GoogleAccessTokenResponse
}
