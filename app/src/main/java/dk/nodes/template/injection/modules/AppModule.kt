package dk.nodes.template.injection.modules

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dk.nodes.template.App
import dk.nodes.template.BuildConfig
import dk.nodes.template.inititializers.AppInitializer
import dk.nodes.template.inititializers.AppInitializerImpl
import javax.inject.Named

@Module
abstract class AppModule {

    @Binds
    abstract fun bindAppInitalizer(initializer: AppInitializerImpl): AppInitializer

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Named("API_KEY")
        fun provideApiKey(): String {
            return BuildConfig.API_KEY
        }

        @JvmStatic
        @Provides
        fun provideContext(application: App): Context = application.applicationContext
    }
}