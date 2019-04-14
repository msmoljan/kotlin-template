package dk.nodes.template.domain.interactors

import dk.nodes.template.repositories.MatchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import techprague.nodes.dk.base.AppCoroutineDispatchers
import techprague.nodes.dk.data.apis.MatchesApi
import techprague.nodes.dk.data.models.body.MatchById
import javax.inject.Inject

class GetMatchByIdInterctor @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val matchRepository: MatchRepository
) : FlowInteractor<Long, MatchById> {
    override suspend fun invoke(executeParams: Long) {

    }

    override fun flow(): Flow<MatchById> {
        return matchRepository.getGetMatchById()
    }

    override val dispatcher: CoroutineDispatcher
        get() = dispatchers.io


}