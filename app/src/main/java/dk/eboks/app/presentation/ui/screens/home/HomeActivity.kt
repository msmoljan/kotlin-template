package dk.eboks.app.presentation.ui.screens.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import dk.eboks.app.R
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.folder.Folder
import dk.eboks.app.domain.models.folder.FolderType
import dk.eboks.app.presentation.base.BaseActivity
import dk.eboks.app.presentation.ui.components.home.folderpreview.RefreshFolderPreviewDoneEvent
import dk.eboks.app.presentation.ui.components.home.folderpreview.RefreshFolderPreviewEvent
import dk.eboks.app.presentation.ui.components.home.channelcontrol.ChannelControlComponentFragment
import dk.eboks.app.presentation.ui.components.home.channelcontrol.RefreshChannelControlDoneEvent
import dk.eboks.app.presentation.ui.components.home.channelcontrol.RefreshChannelControlEvent
import dk.eboks.app.presentation.ui.components.home.folderpreview.FolderPreviewComponentFragment
import dk.eboks.app.presentation.ui.screens.mail.list.MailListActivity
import dk.eboks.app.presentation.ui.screens.profile.ProfileActivity
import dk.eboks.app.util.Starter
import dk.eboks.app.util.putArg
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeContract.View {
    @Inject lateinit var presenter: HomeContract.Presenter

    var folderPreviewFragment : FolderPreviewComponentFragment? = null
    var channelControlFragment : ChannelControlComponentFragment? = null
    var doneRefreshingFolderPreview = false
    var doneRefreshingChannelControls = false

    val onLanguageChange : (Locale)->Unit = { locale ->
        Timber.e("Locale changed to locale")
        updateTranslation()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(dk.eboks.app.R.layout.activity_home)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)
        setupTopBar()
        updateTranslation()

        refreshSrl.isRefreshing = true

        refreshSrl.setOnRefreshListener {
            // fire event to signal ChannelControlComponent and FolderPreviewComponent to refresh
            doneRefreshingChannelControls = false
            doneRefreshingFolderPreview = false
            EventBus.getDefault().post(RefreshFolderPreviewEvent())
            EventBus.getDefault().post(RefreshChannelControlEvent())
        }

        showBtn.setOnClickListener {
            Starter().activity(MailListActivity::class.java).putExtra("folder", Folder(type = FolderType.HIGHLIGHTS)).start()
        }


        presenter.setup()
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    private fun updateTranslation() {
        mainTb.title = Translation.home.windowHeader
    }

    // shamelessly ripped from chnt
    private fun setupTopBar() {
        val menuProfile = mainTb.menu.add(Translation.profile.title)
        menuProfile.setIcon(R.drawable.ic_profile)
        menuProfile.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menuProfile.setOnMenuItemClickListener { item: MenuItem ->
            startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
            true
        }

    }

    override fun getNavigationMenuAction(): Int {
        return R.id.actionHome
    }

    override fun addFolderPreviewComponentFragment(folder : Folder)
    {
        folderPreviewFragment = FolderPreviewComponentFragment().putArg(Folder::class.java.simpleName, folder) as FolderPreviewComponentFragment
        folderPreviewFragment?.let{
            supportFragmentManager.beginTransaction().add(R.id.folderPreviewFl, it, it::class.java.simpleName).commit()
        }
    }

    override fun addChannelControlComponentFragment()
    {
        channelControlFragment = ChannelControlComponentFragment()
        channelControlFragment?.let{
            supportFragmentManager.beginTransaction().add(R.id.channelControlFl, it, it::class.java.simpleName).commit()
        }
    }

    override fun showMailsHeader(show: Boolean) {
        mailHeaderFl.visibility = if(show) View.VISIBLE else View.GONE
    }

    override fun showChannelControlsHeader(show: Boolean) {
        channelsHeaderFl.visibility = if(show) View.VISIBLE else View.GONE
    }

    fun updateRefreshStatus()
    {
        if(doneRefreshingChannelControls && doneRefreshingFolderPreview)
        {
            if(refreshSrl.isRefreshing)
                refreshSrl.isRefreshing = false
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: RefreshFolderPreviewDoneEvent) {
        doneRefreshingFolderPreview = true
        updateRefreshStatus()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: RefreshChannelControlDoneEvent) {
        doneRefreshingChannelControls = true
        updateRefreshStatus()
    }
}