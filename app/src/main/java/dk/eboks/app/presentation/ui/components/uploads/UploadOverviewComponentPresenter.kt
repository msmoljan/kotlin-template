package dk.eboks.app.presentation.ui.components.uploads

import dk.eboks.app.domain.managers.AppStateManager
import dk.nodes.arch.presentation.base.BasePresenterImpl
import javax.inject.Inject

/**
 * Created by bison on 20-05-2017.
 */
class UploadOverviewComponentPresenter @Inject constructor(val appState: AppStateManager) : UploadOverviewComponentContract.Presenter, BasePresenterImpl<UploadOverviewComponentContract.View>() {

    init {
    }

}