package dk.eboks.app.presentation.ui.screens.mail.overview

import dk.eboks.app.domain.models.login.User
import dk.nodes.arch.presentation.base.BasePresenter
import dk.eboks.app.presentation.base.BaseView

/**
 * Created by bison on 07-11-2017.
 */
interface MailOverviewContract {
    interface View : BaseView {
        fun showProgress(show: Boolean)
        fun setUser(user: User?)
    }

    interface Presenter : BasePresenter<View> {
        fun refresh()
    }
}