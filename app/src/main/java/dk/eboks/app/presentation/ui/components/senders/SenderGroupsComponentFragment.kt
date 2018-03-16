package dk.eboks.app.presentation.ui.components.senders

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dk.eboks.app.R
import dk.eboks.app.domain.models.SenderCategory
import dk.eboks.app.domain.models.sender.Sender
import dk.eboks.app.domain.models.sender.SenderGroup
import dk.eboks.app.presentation.base.BaseFragment
import dk.eboks.app.presentation.ui.screens.senders.browse.BrowseCategoryActivity
import kotlinx.android.synthetic.main.fragment_list_component.*
import kotlinx.android.synthetic.main.viewholder_title_subtitle.view.*
import javax.inject.Inject

/**
 * Created by Christian on 3/15/2018.
 * @author   Christian
 * @since    3/15/2018.
 */
class SenderGroupsComponentFragment : BaseFragment(), SenderGroupsComponentContract.View {

    @Inject
    lateinit var presenter: SenderGroupsComponentContract.Presenter

    private val groups = ArrayList<SenderGroup>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_list_component, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)

        listComponentContentLl.removeAllViews()
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)

        val sender = arguments.getSerializable(Sender::class.simpleName) as Sender?
        if (sender != null) {
            sender.groups?.let {
                showSenderGroups(it)
            }
            presenter.getSenderGroups(sender.id)
        }
    }

    override fun setupTranslations() {
        listComponentTitleTv.text = "Somthing"
    }

    override fun showSenderGroups(senderGroups: List<SenderGroup>) {

        listComponentContentLl.removeAllViews()

        senderGroups.forEach {
            val v = inflator.inflate(R.layout.viewholder_title_subtitle, null)
            v.titleTv.text = it.name
            v.subTv.text = when (it.registered) {
                0 -> "not"
                1 -> "registred"
                else -> ""
            }
            v.setOnClickListener { c ->
                val i = Intent(context, BrowseCategoryActivity::class.java)
                i.putExtra(SenderCategory::class.simpleName, it)
                startActivity(i)
            }
            listComponentContentLl.addView(v)
        }
    }

    override fun showError(message: String) {
    }

}