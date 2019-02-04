package dk.eboks.app.presentation.ui.channels.components.content.web

import dk.eboks.app.domain.models.channel.Channel
import dk.eboks.app.presentation.base.BaseView
import dk.nodes.arch.presentation.base.BasePresenter

/**
 * Created by bison on 07-11-2017.
 */
interface ChannelContentComponentContract {
    interface View : BaseView {
        fun showChannel(channel: Channel)
        fun openChannelContent(content: String)
    }

    interface Presenter : BasePresenter<View> {
        fun setup(channel: Channel)
        var currentChannel: Channel?
    }
}