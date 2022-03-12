package com.example.gamewithpaging.di

import com.example.gamewithpaging.Constants.API_KEY
import com.example.gamewithpaging.Constants.AUTH_HEADER
import com.example.gamewithpaging.Constants.BASE_URL
import com.example.gamewithpaging.network.GameApi
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        callFactory: Call.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory(callFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideSiliconApi(retrofit: Retrofit): GameApi {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        @AuthInterceptor authInterceptor: Interceptor
    ): Call.Factory {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .callTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @AuthInterceptor
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest =
                chain.request().newBuilder().addHeader(AUTH_HEADER, API_KEY).build()
            chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(DefaultIfNullFactory())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.CLASS)
    annotation class DefaultIfNull

    class DefaultIfNullFactory : JsonAdapter.Factory {
        override fun create(type: Type, annotations: MutableSet<out Annotation>,
                            moshi: Moshi): JsonAdapter<*>? {
            if (!Types.getRawType(type).isAnnotationPresent(
                    DefaultIfNull::class.java)) {
                return null
            }

            val delegate = moshi.nextAdapter<Any>(this, type, annotations)

            return object : JsonAdapter<Any>() {
                override fun fromJson(reader: JsonReader): Any? {
                    @Suppress("UNCHECKED_CAST")
                    val blob = reader.readJsonValue() as Map<String, Any?>
                    val noNulls = blob.filterValues { it != null }
                    return delegate.fromJsonValue(noNulls)
                }

                override fun toJson(writer: JsonWriter, value: Any?) {
                    return delegate.toJson(writer, value)
                }
            }
        }
    }
    object NULL_TO_EMPTY_STRING_ADAPTER {
        @FromJson
        fun fromJson(reader: JsonReader): String {
            if (reader.peek() != JsonReader.Token.NULL) {
                return reader.nextString()
            }
            reader.nextNull<Unit>()
            return ""
        }
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptor
