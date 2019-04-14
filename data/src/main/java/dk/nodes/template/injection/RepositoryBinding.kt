package dk.nodes.template.injection

import dagger.Binds
import dagger.Module
import dk.nodes.template.network.RestPostRepository
import dk.nodes.template.repositories.MatchRepository
import dk.nodes.template.repositories.MatchRepositoryImpl
import dk.nodes.template.repositories.PostRepository
import javax.inject.Singleton

@Module
abstract class RepositoryBinding {
    @Binds
    @Singleton
    abstract fun bindPostRepository(repository: RestPostRepository): PostRepository

    @Binds
    @Singleton
    abstract fun bindMatchRepository(repository: MatchRepositoryImpl): MatchRepository
}