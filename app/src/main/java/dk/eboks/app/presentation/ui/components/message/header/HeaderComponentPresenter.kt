package dk.eboks.app.presentation.ui.components.message.header

import dk.eboks.app.domain.managers.AppStateManager
import dk.nodes.arch.presentation.base.BasePresenterImpl
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by bison on 20-05-2017.
 */
class HeaderComponentPresenter @Inject constructor(val appState: AppStateManager) : HeaderComponentContract.Presenter, BasePresenterImpl<HeaderComponentContract.View>() {

    init {
        runAction { v->
            appState.state?.currentMessage?.let { v.updateView(it) }
        }
    }

}