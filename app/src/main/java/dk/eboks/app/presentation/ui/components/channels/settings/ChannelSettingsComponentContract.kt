package dk.eboks.app.presentation.ui.components.channels.settings

import dk.eboks.app.domain.models.channel.storebox.StoreboxCreditCard
import dk.eboks.app.domain.models.channel.storebox.StoreboxProfile
import dk.eboks.app.domain.models.shared.Link
import dk.eboks.app.presentation.base.BaseView
import dk.nodes.arch.presentation.base.BasePresenter

/**
 * Created by bison on 07-11-2017.
 */
interface ChannelSettingsComponentContract {
    interface View : BaseView {
        fun setCreditCards(cards: MutableList<StoreboxCreditCard>)
        fun showProgress(boolean: Boolean)
        fun showEmptyView(boolean: Boolean)
        fun setOnlyDigitalReceipts(onlyDigital: Boolean)
        fun showAddCardView(link : Link)
    }

    interface Presenter : BasePresenter<View> {
        fun getCreditCards()
        fun deleteCreditCard(id: String)
        fun getStoreboxProfile()
        fun saveStoreboxProfile(profile : StoreboxProfile)
        fun getStoreboxCardLink()
    }
}