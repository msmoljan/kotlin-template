package dk.nodes.template.domain.interactors

import io.reactivex.Flowable
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.concatenate
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.subscribe

abstract class FlowInteractor<in P: Any, out T : Any> : Interactor<P> {
    private val flow = flowOf<T>()

    private lateinit var params: P

    fun setParams(params: P) {
        this.params = params
        setSource(createObservable(params))
    }

    fun flow(): Flow<T> = flow

    protected abstract fun createObservable(params: P): Flow<T>


    @FlowPreview
    private suspend fun setSource(source: Flow<T>) {
        flow.concatenate(source)
        source.onEach {
            flow.collect(it))
        }
        source.collect(flow::onEach, flow::)
    }
}