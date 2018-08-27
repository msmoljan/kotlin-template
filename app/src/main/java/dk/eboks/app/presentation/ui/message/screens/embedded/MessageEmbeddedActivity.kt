package dk.eboks.app.presentation.ui.message.screens.embedded

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.MenuItem
import dk.eboks.app.R
import dk.eboks.app.domain.managers.EboksFormatter
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.folder.Folder
import dk.eboks.app.domain.models.message.Message
import dk.eboks.app.presentation.base.BaseSheetActivity
import dk.eboks.app.presentation.base.ViewerFragment
import dk.eboks.app.presentation.ui.folder.screens.FolderActivity
import dk.eboks.app.presentation.ui.message.components.detail.attachments.AttachmentsComponentFragment
import dk.eboks.app.presentation.ui.message.components.detail.folderinfo.FolderInfoComponentFragment
import dk.eboks.app.presentation.ui.message.components.detail.header.HeaderComponentFragment
import dk.eboks.app.presentation.ui.message.components.detail.notes.NotesComponentFragment
import dk.eboks.app.presentation.ui.message.components.detail.reply.ReplyButtonComponentFragment
import dk.eboks.app.presentation.ui.message.components.detail.share.ShareComponentFragment
import dk.eboks.app.presentation.ui.message.components.detail.sign.SignButtonComponentFragment
import dk.eboks.app.presentation.ui.message.components.viewers.html.HtmlViewComponentFragment
import dk.eboks.app.presentation.ui.message.components.viewers.image.ImageViewComponentFragment
import dk.eboks.app.presentation.ui.message.components.viewers.pdf.PdfViewComponentFragment
import dk.eboks.app.presentation.ui.message.components.viewers.text.TextViewComponentFragment
import dk.eboks.app.presentation.ui.overlay.screens.ButtonType
import dk.eboks.app.presentation.ui.overlay.screens.OverlayActivity
import dk.eboks.app.presentation.ui.overlay.screens.OverlayButton
import javax.inject.Inject
import kotlinx.android.synthetic.main.include_toolbar.*
import timber.log.Timber

/**
 * Created by bison on 09-02-2018.
 */
class MessageEmbeddedActivity : BaseSheetActivity(), MessageEmbeddedContract.View {
    @Inject
    lateinit var presenter: MessageEmbeddedContract.Presenter

    @Inject
    lateinit var formatter: EboksFormatter

    var headerComponentFragment: HeaderComponentFragment? = null
    var replyButtonComponentFragment: ReplyButtonComponentFragment? = null
    var signButtonComponentFragment: SignButtonComponentFragment? = null
    var shareComponentFragment: ShareComponentFragment? = null
    var notesComponentFragment: NotesComponentFragment? = null
    var attachmentsComponentFragment: AttachmentsComponentFragment? = null
    var folderInfoComponentFragment: FolderInfoComponentFragment? = null
    var embeddedViewerComponentFragment: Fragment? = null

    private var actionButtons = arrayListOf(
            OverlayButton(ButtonType.PRINT)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentSheet(R.layout.sheet_message)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)
        setupTopBar()
        mainHandler.post({ presenter.setup()})

    }

    private fun setupTopBar()
    {
        mainTb.setNavigationIcon(R.drawable.icon_48_chevron_left_red_navigationbar)
        mainTb.title = Translation.message.title
        mainTb.setNavigationOnClickListener {
            onBackPressed()
        }

        val menuItem = mainTb?.menu?.add("_options")
        menuItem?.setIcon(R.drawable.icon_48_option_red_navigationbar)
        menuItem?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menuItem?.setOnMenuItemClickListener { item: MenuItem ->
            val i = Intent(this@MessageEmbeddedActivity, OverlayActivity::class.java)
            i.putExtra("buttons", actionButtons)
            startActivityForResult(i, OverlayActivity.REQUEST_ID)
            true
        }
    }

    override fun setHighPeakHeight() {
        setupPeakHeight(140)
    }

    override fun showTitle(message: Message) {
        mainTb.subtitle = formatter.formatDate(message)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Deal with return from document action sheet
        if (requestCode == OverlayActivity.REQUEST_ID) {
            when (data?.getSerializableExtra("res")) {
                (ButtonType.PRINT) -> {
                    if(embeddedViewerComponentFragment is ViewerFragment)
                        (embeddedViewerComponentFragment as ViewerFragment).print()
                }
                else                -> {
                    // Request do nothing
                }
            }
        }
        // deal with return from folder picker
        /*
        if (requestCode == FolderActivity.REQUEST_ID) {
            data?.extras?.let {
                val moveToFolder = data.getSerializableExtra("res") as Folder
                //Timber.d("Move To Folder ${moveToFolder?.toString()}")
                Timber.e("Returned from folder picker. folder picked: ${moveToFolder.name}")
                presenter.saveReceipt(moveToFolder)
            }
        }
        */
    }

    override fun addHeaderComponentFragment()
    {
        headerComponentFragment = HeaderComponentFragment()
        headerComponentFragment?.let{
            it.arguments = Bundle()
            it.arguments.putBoolean("show_divider", true)
            supportFragmentManager.beginTransaction().add(R.id.sheetComponentsLl, it, HeaderComponentFragment::class.java.simpleName).commit()
        }
    }

    override fun addReplyButtonComponentFragment(message: Message)
    {
        replyButtonComponentFragment = ReplyButtonComponentFragment()
        val args = Bundle()
        args.putSerializable(Message::class.java.simpleName, message)
        replyButtonComponentFragment?.let{
            it.arguments = args
            supportFragmentManager.beginTransaction().add(R.id.sheetComponentsLl, it, ReplyButtonComponentFragment::class.java.simpleName).commit()
        }
    }

    override fun addSignButtonComponentFragment(message: Message)
    {
        signButtonComponentFragment = SignButtonComponentFragment()
        val args = Bundle()
        args.putSerializable(Message::class.java.simpleName, message)
        signButtonComponentFragment?.let{
            it.arguments = args
            supportFragmentManager.beginTransaction().add(R.id.sheetComponentsLl, it, it::class.java.simpleName).commit()
        }
    }

    override fun addShareComponentFragment() {
        shareComponentFragment = ShareComponentFragment()
        shareComponentFragment?.let{
            supportFragmentManager.beginTransaction().add(R.id.sheetComponentsLl, it, ShareComponentFragment::class.java.simpleName).commit()
        }
    }

    override fun addNotesComponentFragment() {
        notesComponentFragment = NotesComponentFragment()
        notesComponentFragment?.let{
            supportFragmentManager.beginTransaction().add(R.id.sheetComponentsLl, it, NotesComponentFragment::class.java.simpleName).commit()
        }
    }

    override fun addAttachmentsComponentFragment() {
        attachmentsComponentFragment = AttachmentsComponentFragment()
        attachmentsComponentFragment?.let {
            supportFragmentManager.beginTransaction().add(R.id.sheetComponentsLl, it, AttachmentsComponentFragment::class.java.simpleName).commit()
        }
    }


    override fun addFolderInfoComponentFragment() {
        folderInfoComponentFragment = FolderInfoComponentFragment()
        folderInfoComponentFragment?.let {
            supportFragmentManager.beginTransaction().add(R.id.sheetComponentsLl, it, FolderInfoComponentFragment::class.java.simpleName).commit()
        }
    }

    override fun addPdfViewer() {
        Handler(mainLooper).post({
            embeddedViewerComponentFragment = PdfViewComponentFragment()
            embeddedViewerComponentFragment?.let {
                supportFragmentManager.beginTransaction().add(R.id.viewerFl, it, PdfViewComponentFragment::class.java.simpleName).commit()
            }
        })
    }

    override fun addImageViewer() {
        Handler(mainLooper).post({
            embeddedViewerComponentFragment = ImageViewComponentFragment()
            embeddedViewerComponentFragment?.let {
                supportFragmentManager.beginTransaction().add(R.id.viewerFl, it, ImageViewComponentFragment::class.java.simpleName).commit()
            }
        })
    }

    override fun addHtmlViewer() {
        Handler(mainLooper).post({
            embeddedViewerComponentFragment = HtmlViewComponentFragment()
            embeddedViewerComponentFragment?.let {
                supportFragmentManager.beginTransaction().add(R.id.viewerFl, it, HtmlViewComponentFragment::class.java.simpleName).commit()
            }
        })
    }

    override fun addTextViewer() {
        Handler(mainLooper).post({
            embeddedViewerComponentFragment = TextViewComponentFragment()
            embeddedViewerComponentFragment?.let {
                supportFragmentManager.beginTransaction().add(R.id.viewerFl, it, TextViewComponentFragment::class.java.simpleName).commit()
            }
        })

    }

    override fun getNavigationMenuAction(): Int { return R.id.actionMail }
}