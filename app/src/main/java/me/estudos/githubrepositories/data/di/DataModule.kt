package me.estudos.githubrepositories.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import me.estudos.githubrepositories.data.repositories.GithubRepoRepository
import me.estudos.githubrepositories.data.repositories.GithubRepoRepositoryImpl
import me.estudos.githubrepositories.data.services.GithubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    private const val OK_HTTP_TAG = "okHttp"

    fun load() {
        loadKoinModules(networkModules() + repositoriesModule())
    }

    private fun networkModules(): Module {

        return module {
            single {
                val interceptor = HttpLoggingInterceptor { Log.i(OK_HTTP_TAG, it) }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<GithubService>(get(), get())
            }
        }
    }

    private fun repositoriesModule(): Module {
        return module {
            single<GithubRepoRepository> { GithubRepoRepositoryImpl(get()) }
        }
    }

    private inline fun <reified T> createService(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): T {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(T::class.java)
    }
}
