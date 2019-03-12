package dk.eboks.app.senders.presentation.ui.screens.overview

import dk.eboks.app.domain.models.SenderCategory
import dk.eboks.app.domain.models.sender.CollectionContainer
import dk.eboks.app.presentation.base.BaseView
import dk.nodes.arch.presentation.base.BasePresenter

/**
 * Created by bison on 07-11-2017.
 */
interface SendersOverviewContract {
    interface View : BaseView {
        fun showCollections(collections: List<CollectionContainer>)
        fun showCategories(categories: List<SenderCategory>)
        fun showSuccess()
        fun showError(s: String)
        fun hidePendingRegistrations()
        fun showPendingRegistrations(pendingRegistrations: List<CollectionContainer>)
    }

    interface Presenter : BasePresenter<View> {
        fun registerSender(senderId: Long)
        fun unregisterSender(senderId: Long)
    }
}