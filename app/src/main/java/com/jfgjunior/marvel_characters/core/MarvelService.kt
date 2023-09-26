package com.jfgjunior.marvel_characters.core

import com.jfgjunior.marvel_characters.BuildConfig
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.MessageDigest
import javax.inject.Inject

class MarvelService @Inject constructor() {
    fun provideMarvelApi(): MarvelApi {
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val original: Request = chain.request()
                        val originalHttpUrl: HttpUrl = original.url
                        val ts = System.currentTimeMillis().toString()
                        val hash = "$ts${BuildConfig.PRIVATE_KEY}${BuildConfig.PUBLIC_KEY}".toMD5()
                        val url = originalHttpUrl.newBuilder()
                            .addQueryParameter(API_KEY_QUERY, BuildConfig.PUBLIC_KEY)
                            .addQueryParameter(HASH_QUERY, hash)
                            .addQueryParameter(TIMESTAMP_QUERY, ts)
                            .build()
                        val requestBuilder: Request.Builder = original.newBuilder()
                            .url(url)
                        val request: Request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .build()
            .create(MarvelApi::class.java)
    }

    private fun String.toMD5(): String {
        val bytes = MessageDigest.getInstance(ALGORITHM_TYPE).digest(this.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    companion object {
        private const val ALGORITHM_TYPE = "MD5"
        private const val API_KEY_QUERY = "apikey"
        private const val HASH_QUERY = "hash"
        private const val TIMESTAMP_QUERY = "ts"
        private const val URL = "https://gateway.marvel.com/"
    }
}