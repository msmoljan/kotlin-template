package dk.nodes.template.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dk.nodes.template.domain.interactors.GetMatchByIdInterctor
import dk.nodes.template.domain.interactors.launchInteractor
import dk.nodes.template.presentation.ui.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val getMatchByIdInterctor: GetMatchByIdInterctor
) : BaseViewModel() {

    private val _viewState = MutableLiveData<MainActivityViewState>()
    val viewState: LiveData<MainActivityViewState> = _viewState

    fun fetchPosts() = scope.launchInteractor(getMatchByIdInterctor, 4354152067L) {

    }
}