package info.imdang.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import info.imdang.data.BuildConfig
import info.imdang.data.constant.API_SERVER
import info.imdang.data.constant.GOOGLE_API_SERVER
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

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
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
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
