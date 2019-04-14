package dk.nodes.template.repositories

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import techprague.nodes.dk.data.models.body.MatchById

@FlowPreview
interface MatchRepository {

    suspend fun getGetMatchById(id: Long): Flow<MatchById>

}