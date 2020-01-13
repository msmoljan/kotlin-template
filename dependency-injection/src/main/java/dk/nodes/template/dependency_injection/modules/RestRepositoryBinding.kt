package dk.nodes.template.dependency_injection.modules

import dagger.Binds
import dagger.Module
import dk.nodes.template.network.RestPostRepository
import dk.nodes.template.repositories.PostRepository
import javax.inject.Singleton

@Module
abstract class RestRepositoryBinding {
    @Binds
    @Singleton
    abstract fun bindPostRepository(repository: RestPostRepository): PostRepository
}