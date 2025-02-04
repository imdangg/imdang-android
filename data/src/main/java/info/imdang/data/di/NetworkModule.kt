package info.imdang.data.di

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.imdang.data.BuildConfig
import info.imdang.data.constant.API_SERVER
import info.imdang.data.constant.ErrorCode
import info.imdang.data.constant.ResponseCode
import info.imdang.data.constant.GOOGLE_API_SERVER
import info.imdang.data.datasource.lcoal.AuthLocalDataSource
import info.imdang.data.datasource.remote.AuthRemoteDataSource
import info.imdang.data.interceptor.TokenInterceptor
import info.imdang.data.model.request.auth.ReissueTokenRequest
import info.imdang.data.model.response.common.ErrorResponse
import java.net.ConnectException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private var lastTokenRefreshedAt = 0L

    private val _networkErrorResponse = MutableSharedFlow<ErrorResponse>()
    private val networkErrorResponse = _networkErrorResponse.asSharedFlow()

    @Provides
    @Singleton
    fun providesNetworkErrorResponse(): SharedFlow<ErrorResponse> = networkErrorResponse

    @Provides
    @Singleton
    @Named("network_error")
    fun providesNetworkErrorInterceptor(
        @ApplicationContext context: Context,
        authLocalDataSource: AuthLocalDataSource,
        authRemoteDataSource: Provider<AuthRemoteDataSource>
    ): Interceptor = Interceptor { chain ->
        val request = chain.request()
        try {
            val response = chain.proceed(request)
            if (!response.isSuccessful) {
                handleNetworkError(
                    context = context,
                    response = response,
                    authLocalDataSource = authLocalDataSource,
                    authRemoteDataSource = authRemoteDataSource.get()
                )?.let {
                    return@Interceptor chain.proceed(it)
                }
            }

            response
        } catch (e: ConnectException) {
            emitNetworkErrorResponse(
                ErrorResponse(
                    code = "NetworkException",
                    message = e.message ?: "네트워크 연결 상태를 확인해 주세요"
                )
            )
            chain.proceed(request)
        }
    }

    private fun handleNetworkError(
        context: Context,
        response: Response,
        authLocalDataSource: AuthLocalDataSource,
        authRemoteDataSource: AuthRemoteDataSource
    ): Request? {
        val bodyString = response.peekBody(Long.MAX_VALUE).string()
        try {
            val error = Gson().fromJson(bodyString, ErrorResponse::class.java)
            when (response.code) {
                ResponseCode.UNAUTHORIZED -> {
                    when (ErrorCode.fromString(error.code)) {
                        ErrorCode.J001, ErrorCode.J003 -> {
                            if (response.request.url.pathSegments.contains("reissue")) {
                                emitNetworkErrorResponse(error)
                                return null
                            } else {
                                reissueToken(
                                    authLocalDataSource = authLocalDataSource,
                                    authRemoteDataSource = authRemoteDataSource
                                )
                            }
                            return response
                                .request
                                .newBuilder()
                                .build()
                        }
                        else -> {
                            emitNetworkErrorResponse(error)
                        }
                    }
                }
                ResponseCode.INTERNAL_SERVER_ERROR -> {
                    emitNetworkErrorResponse(error)
                }
                ResponseCode.NETWORK_NOT_CONNECTED -> {
                    showNetworkErrorToast(context, response)
                }
                else -> {
                    emitNetworkErrorResponse(error)
                }
            }
            return null
        } catch (e: Exception) {
            return null
        }
    }

    private fun reissueToken(
        authLocalDataSource: AuthLocalDataSource,
        authRemoteDataSource: AuthRemoteDataSource
    ) {
        synchronized(this) {
            val current = System.currentTimeMillis()
            if (current - lastTokenRefreshedAt >= 5000) {
                runBlocking {
                    authRemoteDataSource.reissueToken(
                        reissueTokenRequest = ReissueTokenRequest(
                            memberId = authLocalDataSource.getMemberId(),
                            refreshToken = authLocalDataSource.getRefreshToken()
                        )
                    )?.let { tokenResponse ->
                        authLocalDataSource.saveAccessToken(tokenResponse.accessToken)
                        authLocalDataSource.saveRefreshToken(tokenResponse.refreshToken)
                    }
                }
                lastTokenRefreshedAt = System.currentTimeMillis()
            }
        }
    }

    private fun showNetworkErrorToast(context: Context, response: Response) {
        CoroutineScope(Dispatchers.Main).launch {
            val message = response.message.ifBlank { "네트워크 연결 상태를 확인해 주세요" }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun emitNetworkErrorResponse(error: ErrorResponse) {
        CoroutineScope(Dispatchers.Main).launch {
            _networkErrorResponse.emit(error)
        }
    }

    @Provides
    @Singleton
    fun providesTokenInterceptor(
        authLocalDataSource: AuthLocalDataSource
    ): TokenInterceptor = TokenInterceptor(authLocalDataSource)

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor(
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (!message.startsWith("{") && !message.startsWith("[")) {
                    Log.d("OkHttp", message)
                    return
                }

                try {
                    Log.d(
                        "OkHttp",
                        GsonBuilder()
                            .setPrettyPrinting()
                            .create()
                            .toJson(JsonParser.parseString(message))
                    )
                } catch (m: JsonSyntaxException) {
                    Log.e("OkHttp", message)
                }
            }
        }
    ).apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    @Named("imdang")
    fun providesOkHttpClient(
        @Named("network_error") networkErrorInterceptor: Interceptor,
        tokenInterceptor: TokenInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(networkErrorInterceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("google")
    fun providesGoogleOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Named("imdang")
    fun providesRetrofit(
        @Named("imdang") okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_SERVER)
            .build()
    }

    @Provides
    @Singleton
    @Named("google")
    fun providesGoogleRetrofit(
        @Named("google") okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GOOGLE_API_SERVER)
            .build()
    }
}
