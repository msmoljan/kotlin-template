package dk.nodes.template.repositories

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import techprague.nodes.dk.data.apis.MatchesApi
import techprague.nodes.dk.data.models.body.MatchById
import javax.inject.Inject

@FlowPreview
class MatchRepositoryImpl @Inject constructor(private val matchesApi: MatchesApi) :
    MatchRepository {

    private val matchMap = mutableMapOf<Long, Flow<MatchById>>()

    override suspend fun getGetMatchById(id: Long): Flow<MatchById> {
        return matchMap[id] ?: {
            val result = matchesApi.matchesMatchIdGet(id).execute()
            val flow = flowOf(result.body()!!)
            matchMap[id] = flow
            flow
        }.invoke()
    }
}