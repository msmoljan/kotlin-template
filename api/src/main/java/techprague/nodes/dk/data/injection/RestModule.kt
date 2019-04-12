package techprague.nodes.dk.data.injection

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import techprague.nodes.dk.data.apis.BenchmarksApi
import techprague.nodes.dk.data.apis.DistributionsApi
import techprague.nodes.dk.data.apis.ExplorerApi
import techprague.nodes.dk.data.apis.FeedApi
import techprague.nodes.dk.data.apis.HealthApi
import techprague.nodes.dk.data.apis.HeroStatsApi
import techprague.nodes.dk.data.apis.HeroesApi
import techprague.nodes.dk.data.apis.LeaguesApi
import techprague.nodes.dk.data.apis.LiveApi
import techprague.nodes.dk.data.apis.MatchesApi
import techprague.nodes.dk.data.apis.MetadataApi
import techprague.nodes.dk.data.apis.PlayersApi
import techprague.nodes.dk.data.apis.ProMatchesApi
import techprague.nodes.dk.data.apis.ProPlayersApi
import techprague.nodes.dk.data.apis.PublicMatchesApi
import techprague.nodes.dk.data.apis.RankingsApi
import techprague.nodes.dk.data.apis.RecordsApi
import techprague.nodes.dk.data.apis.ReplaysApi
import techprague.nodes.dk.data.apis.RequestApi
import techprague.nodes.dk.data.apis.ScenariosApi
import techprague.nodes.dk.data.apis.SchemaApi
import techprague.nodes.dk.data.apis.SearchApi
import techprague.nodes.dk.data.apis.StatusApi
import techprague.nodes.dk.data.apis.TeamsApi
import javax.inject.Named
import javax.inject.Singleton

@Module
class RestModule {

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.opendota.com/api/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun provideOkhttpClient(@Named("API_KEY") apiKey: String): OkHttpClient {
        return OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                var request = chain.request()
//                val url = request.url().newBuilder().addQueryParameter("api_key", apiKey).build()
//                request = request.newBuilder().url(url).build()
//                chain.proceed(request)
//            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideBenchmarksApi(retrofit: Retrofit): BenchmarksApi {
        return retrofit.create(BenchmarksApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDistributionsApi(retrofit: Retrofit): DistributionsApi {
        return retrofit.create(DistributionsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideExplorerApi(retrofit: Retrofit): ExplorerApi {
        return retrofit.create(ExplorerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFeedApi(retrofit: Retrofit): FeedApi {
        return retrofit.create(FeedApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHealthApi(retrofit: Retrofit): HealthApi {
        return retrofit.create(HealthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHeroesApi(retrofit: Retrofit): HeroesApi {
        return retrofit.create(HeroesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHeroStatsApi(retrofit: Retrofit): HeroStatsApi {
        return retrofit.create(HeroStatsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLeaguesApi(retrofit: Retrofit): LeaguesApi {
        return retrofit.create(LeaguesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLiveApi(retrofit: Retrofit): LiveApi {
        return retrofit.create(LiveApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMatchesApi(retrofit: Retrofit): MatchesApi {
        return retrofit.create(MatchesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMetadataApi(retrofit: Retrofit): MetadataApi {
        return retrofit.create(MetadataApi::class.java)
    }

    @Provides
    @Singleton
    fun providePlayersApi(retrofit: Retrofit): PlayersApi {
        return retrofit.create(PlayersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProMatchesApi(retrofit: Retrofit): ProMatchesApi {
        return retrofit.create(ProMatchesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProPlayersApi(retrofit: Retrofit): ProPlayersApi {
        return retrofit.create(ProPlayersApi::class.java)
    }

    @Provides
    @Singleton
    fun providePublicMatchesApi(retrofit: Retrofit): PublicMatchesApi {
        return retrofit.create(PublicMatchesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRankingsApi(retrofit: Retrofit): RankingsApi {
        return retrofit.create(RankingsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecordsApi(retrofit: Retrofit): RecordsApi {
        return retrofit.create(RecordsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReplaysApi(retrofit: Retrofit): ReplaysApi {
        return retrofit.create(ReplaysApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRequestApi(retrofit: Retrofit): RequestApi {
        return retrofit.create(RequestApi::class.java)
    }

    @Provides
    @Singleton
    fun provideScenariosApi(retrofit: Retrofit): ScenariosApi {
        return retrofit.create(ScenariosApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSchemaApi(retrofit: Retrofit): SchemaApi {
        return retrofit.create(SchemaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStatusApi(retrofit: Retrofit): StatusApi {
        return retrofit.create(StatusApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTeamsApi(retrofit: Retrofit): TeamsApi {
        return retrofit.create(TeamsApi::class.java)
    }
}