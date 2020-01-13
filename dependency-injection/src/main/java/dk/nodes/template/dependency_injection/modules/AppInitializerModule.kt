package dk.nodes.template.dependency_injection.modules

import dagger.Module
import dagger.Provides
import dk.nodes.template.app_context.AppInitializer
import dk.nodes.template.nstack.NStackAppInitializer

@Module
class AppInitializerModule {

    @Provides
    fun provideAppInitializers(): Iterable<AppInitializer> {
        return listOf(
            NStackAppInitializer()
        )
    }
}