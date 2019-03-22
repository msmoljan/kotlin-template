package dk.eboks.app.presentation.ui.mail.components.maillist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dk.eboks.app.BuildConfig
import dk.eboks.app.R
import dk.eboks.app.domain.managers.EboksFormatter
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.folder.Folder
import dk.eboks.app.domain.models.folder.FolderType
import dk.eboks.app.domain.models.message.Message
import dk.eboks.app.domain.models.message.MessageType
import dk.eboks.app.domain.models.sender.Sender
import dk.eboks.app.mail.presentation.ui.components.maillist.MailListComponentContract
import dk.eboks.app.presentation.base.BaseFragment
import dk.eboks.app.presentation.ui.folder.screens.FolderActivity
import dk.eboks.app.presentation.ui.mail.components.maillist.MailMessagesAdapter.MailMessageEvent.MOVE
import dk.eboks.app.presentation.ui.mail.components.maillist.MailMessagesAdapter.MailMessageEvent.OPEN
import dk.eboks.app.presentation.ui.mail.components.maillist.MailMessagesAdapter.MailMessageEvent.READ
import dk.eboks.app.presentation.ui.message.screens.opening.MessageOpeningActivity
import dk.eboks.app.presentation.ui.overlay.screens.ButtonType
import dk.eboks.app.presentation.ui.overlay.screens.OverlayActivity
import dk.eboks.app.presentation.ui.overlay.screens.OverlayButton
import dk.eboks.app.presentation.widgets.DividerItemDecoration
import dk.eboks.app.util.BundleKeys
import dk.eboks.app.util.EndlessRecyclerViewScrollListener
import dk.eboks.app.util.FragmentArguments
import dk.eboks.app.util.Starter
import dk.eboks.app.util.guard
import dk.eboks.app.util.visible
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_mail_list_component.*
import kotlinx.android.synthetic.main.include_toolbar.*
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

class MailListComponentFragment : BaseFragment(), MailListComponentContract.View {
    @Inject lateinit var presenter: MailListComponentContract.Presenter
    @Inject lateinit var formatter: EboksFormatter
    @Inject lateinit var adapter: MailMessagesAdapter

    @VisibleForTesting internal var checkedList: MutableList<Message> = ArrayList()

    private var editAction: ButtonType? = null
    private var menuProfile: MenuItem? = null

    private var componentListener: MailListComponentListener? = null

    private var scrollListener: EndlessRecyclerViewScrollListener? = null

    private lateinit var fragmentArguments: Arguments

    var folder: Folder? = null
        set(value) {
            field = value
            adapter.folder = value
        }
    private var modeEdit: Boolean = false
        set(value) {
            field = value
            adapter.editMode = value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mail_list_component, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        componentListener = context as? MailListComponentListener
    }

    override fun onDetach() {
        super.onDetach()
        componentListener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        arguments.guard {
            onBackPressed()
            return
        }?.let { bundle -> fragmentArguments = bundle.getParcelable(BundleKeys.arguments)!! }
        presenter.onViewCreated(this, lifecycle)
        setupRecyclerView()
        setupFab()
        checkFabState()
        refreshSrl.setOnRefreshListener {
            scrollListener?.resetState()
            presenter.refresh()
        }
        fragmentArguments.folder?.let(presenter::setup)
        fragmentArguments.sender?.let(presenter::setup)

        folder = fragmentArguments.folder
        setupTopBar()
    }

    private fun setupFab() {
        mainFab.setOnClickListener {
            startActivityForResult(
                OverlayActivity.createIntent(it.context, getActonButtons()),
                OverlayActivity.REQUEST_ID
            )
        }
    }

    private fun getActonButtons(): ArrayList<OverlayButton> {
        val actionButtons = arrayListOf(
            OverlayButton(ButtonType.MOVE),
            OverlayButton(ButtonType.DELETE)

        )
        val showRead = checkedList.any { it.unread && it.type != MessageType.UPLOAD }
        val showUnread = checkedList.any { !it.unread && it.type != MessageType.UPLOAD }
        val showArchive =
            checkedList.any { it.type != MessageType.UPLOAD } && folder?.type == FolderType.INBOX

        if (showRead) actionButtons.add(OverlayButton(ButtonType.READ))
        if (showUnread) actionButtons.add(OverlayButton(ButtonType.UNREAD))
        if (showArchive) actionButtons.add(OverlayButton(ButtonType.ARCHIVE))

        return actionButtons
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Deal with return from document action sheet
        if (requestCode == OverlayActivity.REQUEST_ID) {
            when (data?.getSerializableExtra("res")) {
                (ButtonType.MOVE) -> {
                    editAction = ButtonType.MOVE
                    startFolderSelectActivity()
                }
                (ButtonType.DELETE) -> {
                    editAction = ButtonType.DELETE
                    presenter.deleteMessages(checkedList)
                    toggleEditMode()
                }
                (ButtonType.ARCHIVE) -> {
                    editAction = ButtonType.ARCHIVE
                    presenter.archiveMessages(checkedList)
                    toggleEditMode()
                }
                (ButtonType.READ) -> {
                    editAction = ButtonType.READ
                    presenter.markReadMessages(checkedList, false)
                    toggleEditMode()
                }
                (ButtonType.UNREAD) -> {
                    editAction = ButtonType.UNREAD
                    presenter.markReadMessages(checkedList, true)
                    toggleEditMode()
                }
                else -> {
                    // Request do nothing
                    editAction = null
                    toggleEditMode()
                }
            }
        }

        // deal with return from folder picker
        if (requestCode == FolderActivity.REQUEST_ID) {
            data?.extras?.let {
                val moveToFolder = data.getSerializableExtra("res") as Folder
                presenter.moveMessages(moveToFolder.id, checkedList)
                checkedList.clear()
                if (modeEdit) {
                    toggleEditMode()
                }
            }
        }
    }

    private fun setupTopBar() {
        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getBaseActivity()?.mainTb?.menu?.clear()

        getBaseActivity()?.mainTb?.setNavigationIcon(R.drawable.icon_48_chevron_left_red_navigationbar)
        getBaseActivity()?.mainTb?.setNavigationOnClickListener {
            getBaseActivity()?.mainTb?.navigationIcon = null
            onBackPressed()
        }

        if (fragmentArguments.isEdit && BuildConfig.ENABLE_DOCUMENT_ACTIONS) {
            menuProfile = getBaseActivity()?.mainTb?.menu?.add(Translation.uploads.topbarEdit)
            menuProfile?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            menuProfile?.setOnMenuItemClickListener { item: MenuItem ->
                toggleEditMode()
                true
            }
        }
        setTopBar()
    }

    private fun onBackPressed() {
        fragmentManager?.popBackStack()
    }

    private fun toggleEditMode() {
        modeEdit = !modeEdit
        refreshSrl?.isEnabled = !modeEdit
        checkedList.clear()
        setTopBar()
        componentListener?.onEditModeActive(modeEdit)
        checkFabState()
        messagesRv.adapter?.notifyDataSetChanged()
    }

    private fun checkFabState() {
        if (checkedList.size > 0) {
            mainFab.show()
        } else {
            mainFab.hide()
        }
        setTopBar()
    }

    private fun setTopBar() {
        activity?.run {
            if (modeEdit) {
                mainTb.setNavigationIcon(R.drawable.icon_48_close_red_navigationbar)
                menuProfile?.isVisible = false

                mainTb.setNavigationOnClickListener {
                    toggleEditMode()
                }
            } else {
                mainTb.setNavigationIcon(R.drawable.icon_48_chevron_left_red_navigationbar)
                menuProfile?.isVisible = true
                mainTb.setNavigationOnClickListener {
                    mainTb.navigationIcon = null
                    onBackPressed()
                }
            }
            if (checkedList.size > 0) {
                mainTb.title = checkedList.size.toString() + " " + Translation.uploads.chosen
            } else {
                if (modeEdit) {
                    mainTb.title = Translation.inbox.chooseMails
                } else {
                    folder?.let {
                        when (it.type) {
                            FolderType.UPLOADS -> {
                                mainTb.title = Translation.uploads.title
                            }
                            else -> {
                                mainTb.title = it.name
                            }
                        }
                    }.guard {
                        fragmentArguments.sender?.name?.let(mainTb::setTitle)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter.showUploads = fragmentArguments.showUploads
        val layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
        messagesRv.layoutManager = layoutManager
        messagesRv.adapter = adapter
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                presenter.loadNextPage()
            }
        }
        messagesRv.addOnScrollListener(scrollListener!!)
        messagesRv.addItemDecoration(
            DividerItemDecoration(
                drawable = ContextCompat.getDrawable(context!!, R.drawable.shape_divider)!!,
                indentationDp = 72,
                backgroundColor = ContextCompat.getColor(context!!, R.color.white)
            )
        )

        adapter.onActionEvent = { message, mailMessageEvent ->
            Timber.d("onActionEvent: %s", mailMessageEvent)
            when (mailMessageEvent) {
                OPEN -> {
                    editAction = ButtonType.OPEN
                    message.unread = false
                    startMessageOpenActivity(message)
                }
                READ -> {
                    editAction = ButtonType.READ
                    message.unread = !message.unread
                    presenter.markReadMessages(arrayListOf(message), message.unread)
                }
                MOVE -> {
                    editAction = ButtonType.MOVE
                    checkedList.clear()
                    checkedList.add(message)
                    startFolderSelectActivity()
                }
            }
        }

        adapter.onMessageCheckedChanged = { isSelected, message ->
            if (isSelected) {
                checkedList.add(message)
            } else {
                checkedList.remove(message)
            }

            checkFabState()
        }
    }

    private fun startMessageOpenActivity(message: Message) {
        activity?.run {
            Starter()
                .activity(MessageOpeningActivity::class.java)
                .putExtra(Message::class.java.simpleName, message)
                .start()
        }
    }

    private fun startFolderSelectActivity() {
        val i = Intent(context, FolderActivity::class.java)
        i.putExtra("pick", true)
        startActivityForResult(i, FolderActivity.REQUEST_ID)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.mainTb?.menu?.clear()
        activity?.mainTb?.title = ""
    }

    override fun showRefreshProgress(show: Boolean) {
        refreshSrl.isRefreshing = show
    }

    override fun showProgress(show: Boolean) {
        progressFl.visible = show
    }

    override fun showEmpty(show: Boolean) {
        emptyFl.visible = show
        contentFl.visible = !show
    }

    override fun showMessages(messages: List<Message>) {
        Timber.e("Showing messages")
        checkedList.clear()
        adapter.setData(messages)
    }

    override fun appendMessages(messages: List<Message>) {
        Timber.e("Appending messages")
        adapter.addMessages(messages)
    }

    companion object {
        var refreshOnResume: Boolean = false
        @JvmStatic
        fun newInstance(fragmentArguments: Arguments): MailListComponentFragment {
            return MailListComponentFragment().apply {
                this.arguments = fragmentArguments.toBundle()
            }
        }
    }

    @Parcelize
    data class Arguments(
        val folder: Folder? = null,
        val showUploads: Boolean = false,
        val sender: Sender? = null,
        val isEdit: Boolean = true
    ) : Parcelable, FragmentArguments

    interface MailListComponentListener {
        fun onEditModeActive(active: Boolean)
    }
}