package dk.nodes.template.presentation.ui.sample

import androidx.lifecycle.viewModelScope
import dk.nodes.template.domain.interactors.*
import dk.nodes.template.models.Theme
import dk.nodes.template.presentation.ui.base.BaseViewModel
import dk.nodes.template.presentation.util.SingleEvent
import dk.nodes.template.presentation.util.ThemeHelper
import dk.nodes.template.presentation.util.ViewErrorController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SampleViewModel @Inject constructor(
    private val postsInteractor: PostFlowInteractor,
    private val getThemeInteractor: GetThemeInteractor,
    private val setThemeInteractor: SetThemeInteractor
) : BaseViewModel<SampleViewState>() {

    override val initState: SampleViewState = SampleViewState()

    init {
        viewModelScope.launch {
            postsInteractor.flow().collect {
                state = state.copy(posts = it)
            }
        }
    }

    fun fetchPosts() {
        viewModelScope.launch(Dispatchers.Main) {
            state = mapResult(Loading())
            val result = runInteractor(postsInteractor.asResult())
            state = mapResult(result)
        }
    }

    fun switchTheme() {
        viewModelScope.launch {
            val newTheme = if (getThemeInteractor.invoke() == Theme.LIGHT) Theme.DARK else Theme.LIGHT
            setThemeInteractor.invoke(newTheme)
            ThemeHelper.applyTheme(newTheme)
        }
    }

    private fun mapResult(result: InteractorResult<Unit>): SampleViewState {
        return when (result) {
            is Success -> state.copy(isLoading = false)
            is Loading -> state.copy(isLoading = true)
            is Fail -> state.copy(
                viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                isLoading = false
            )
            else -> SampleViewState()
        }
    }
}