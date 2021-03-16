package it.facile.records.agent.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.speekha.httpmocker.Mode
import fr.speekha.httpmocker.model.ResponseDescriptor
import fr.speekha.httpmocker.okhttp.MockResponseInterceptor
import fr.speekha.httpmocker.okhttp.builder.mockInterceptor
import it.facile.records.agent.BuildConfig
import it.facile.records.agent.domain.repository.network.RestApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * This is the main Hilt modules. I'll keep the Kotlin serializations, Retrofit
 * and okHttp libs even if the remote repo is mocked
 *
 *
 * */
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    //TODO: Attach HTTPMOCK https://github.com/speekha/httpmocker
    const val BASE_URL = "https://www.facile.test.it/"

    @Singleton
    @Provides
    fun provideRetrofit(json: Json, okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json"
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType.toMediaType()))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideJsonConfiguration(): Json {
        return Json {
            encodeDefaults = false
            ignoreUnknownKeys = true
            isLenient = false
            allowStructuredMapKeys = false
            prettyPrint = true
            useArrayPolymorphism = false
            classDiscriminator = ""
        }
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptors: ArrayList<Interceptor>): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideInterceptors(loggingInterceptor: HttpLoggingInterceptor,mockResponseInterceptor: MockResponseInterceptor): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        interceptors.add(loggingInterceptor)
        interceptors.add(mockResponseInterceptor)
        return interceptors
    }

    @Singleton
    @Provides
    fun provideLogginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideHttpMockeryInterceptor(): MockResponseInterceptor {
        return mockInterceptor {
            useDynamicMocks{
                ResponseDescriptor(delay = 1000L, code = 200, "application/json", body ="""
                    {
                       "records":[
                          {
                             "id":10,
                             "record_name":"Passaporto"
                          },
                          {
                             "id":11,
                             "record_name":"Contratto"
                          }
                       ]
                    }
                """.trimIndent())
//                ResponseDescriptor(500L,code = 404) //TODO: test this
            }
            setMode(Mode.ENABLED)
        }
    }


}
