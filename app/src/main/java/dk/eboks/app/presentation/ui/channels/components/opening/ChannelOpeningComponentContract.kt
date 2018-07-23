package dk.eboks.app.presentation.ui.channels.components.opening

import dk.eboks.app.domain.config.LoginProvider
import dk.eboks.app.domain.models.channel.Channel
import dk.eboks.app.presentation.base.BaseView
import dk.nodes.arch.presentation.base.BasePresenter

/**
 * Created by bison on 07-11-2017.
 */
interface ChannelOpeningComponentContract {
    interface View : BaseView {
        fun showOpenState(channel : Channel)
        fun showDisabledState(channel: Channel)
        fun showInstallState(channel: Channel)
        fun showVerifyState(channel: Channel, provider : LoginProvider)
        fun showProgress(show : Boolean)
        fun showRequirementsDrawer(channel: Channel)
        fun openChannelContent(channel : Channel)
        fun openStoreBoxContent(channel : Channel)
        fun openEkeyContent()
        fun showStoreboxUserAlreadyExists()
    }

    interface Presenter : BasePresenter<View> {
        fun setup(channelId : Int)
        fun install(channel : Channel)
        fun open(channel: Channel)
        fun refreshChannel()
    }
}