package dk.eboks.app.presentation.ui.screens.message.opening

import dk.eboks.app.domain.models.message.Message
import dk.eboks.app.presentation.base.BaseFragment
import dk.nodes.arch.presentation.base.BasePresenter
import dk.eboks.app.presentation.base.BaseView

/**
 * Created by bison on 07-11-2017.
 */
interface MessageOpeningContract {
    interface View : BaseView {
        fun setOpeningFragment(cls : Class<out BaseFragment>)
        fun finish()
    }

    interface Presenter : BasePresenter<View> {
        fun setup(msg : Message)
        fun signalMessageOpenDone()
    }
}