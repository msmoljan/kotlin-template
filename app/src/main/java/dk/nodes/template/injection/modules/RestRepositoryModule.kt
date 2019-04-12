package dk.nodes.template.injection.modules

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dk.nodes.template.network.Api
import dk.nodes.template.network.RestPostRepository
import dk.nodes.template.repositories.PostRepository
import javax.inject.Singleton

@Module
class RestRepositoryModule {
    @Provides
    @Singleton
    fun providePostRepository(
        api: Api,
        gson: Gson,
        context: Context
    ): PostRepository {
        return RestPostRepository(api)
    }
}