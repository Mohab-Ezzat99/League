package mrandroid.league.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mrandroid.league.BuildConfig
import mrandroid.league.data.local.Converters
import mrandroid.league.data.local.LeagueDB
import mrandroid.league.data.remote.LeagueApi
import mrandroid.league.data.repository.LeagueRepositoryImpl
import mrandroid.league.domain.repository.LeagueRepository
import mrandroid.league.util.Constants
import mrandroid.league.util.parser.GsonParser
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): LeagueApi =
        Retrofit.Builder()
            .baseUrl(LeagueApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(LeagueApi::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor { chain: Interceptor.Chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("X-Auth-Token", BuildConfig.LEAGUE_API_KEY)
                    .build()
                chain.proceed(newRequest)
            }.build()

    @Provides
    @Singleton
    fun provideLeagueDB(app: Application): LeagueDB {
        return Room.databaseBuilder(app, LeagueDB::class.java, Constants.DATABASE_NAME)
            .addTypeConverter(Converters(GsonParser(Gson())))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideWordRepository(
        LeagueApi: LeagueApi,
        LeagueDB: LeagueDB
    ): LeagueRepository {
        return LeagueRepositoryImpl(LeagueApi, LeagueDB.dao)
    }

}