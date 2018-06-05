package dk.eboks.app.presentation.ui.components.senders.list

import android.arch.lifecycle.Lifecycle
import dk.eboks.app.domain.interactors.sender.GetSendersInteractor
import dk.eboks.app.domain.managers.AppStateManager
import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.models.sender.Sender
import dk.nodes.arch.presentation.base.BasePresenterImpl
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Created by bison on 20-05-2017.
 */
class SenderAllListComponentPresenter @Inject constructor(val appState: AppStateManager, val getSendersInteractor: GetSendersInteractor) : SenderAllListComponentContract.Presenter, BasePresenterImpl<SenderAllListComponentContract.View>(), GetSendersInteractor.Output {

    var senders: MutableList<Sender> = ArrayList()
    var filteredSenders: MutableList<Sender> = ArrayList()

    init {
        refresh()
        runAction { v -> v.showProgress(true) }
    }

    override fun refresh() {
        getSendersInteractor.input = GetSendersInteractor.Input()
        getSendersInteractor.output = this
        getSendersInteractor.run()
    }

    override fun onGetSenders(senders: List<Sender>) {

        saveSenders(senders)

        runAction { v ->
            v.showProgress(false)
            if (senders.isNotEmpty()) {
                v.showEmpty(false)
                v.showSenders(filteredSenders)
            } else {
                v.showEmpty(true)
            }
        }
    }

    private fun saveSenders(senders: List<Sender>) {
        this.senders.clear()
        this.filteredSenders.clear()
        this.senders.addAll(senders)
        filteredSenders.addAll(this.senders)
    }

    override fun onGetSendersError(error: ViewError) {
        runAction { v ->
            v.showProgress(false)
            v.showErrorDialog(error)
        }
    }

    override fun loadAllSenders() {
        filteredSenders.clear()
        filteredSenders.addAll(senders)
        runAction { v->
            v.showSenders(filteredSenders)
        }
    }

    override fun searchSenders(searchText: String) {
        filteredSenders.clear()
        for (sender in senders) {
            if (sender.name.contains(searchText, true)) {
                filteredSenders.add(sender)
            }
        }
        runAction { v->
            v.showSenders(filteredSenders)
        }
    }

}