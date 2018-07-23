package dk.eboks.app.presentation.ui.channels.components.overview

import android.animation.Animator
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dk.eboks.app.R
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.channel.Channel
import dk.eboks.app.domain.models.channel.ChannelColor
import dk.eboks.app.presentation.base.BaseFragment
import dk.eboks.app.presentation.ui.channels.screens.content.ChannelContentActivity
import dk.eboks.app.util.Starter
import dk.eboks.app.util.setVisible
import kotlinx.android.synthetic.main.fragment_channel_list_component.*
import javax.inject.Inject

/**
 * Created by bison on 09-02-2018.
 */
class ChannelOverviewComponentFragment : BaseFragment(), ChannelOverviewComponentContract.View {

    var cards: MutableList<Channel> = ArrayList()

    @Inject
    lateinit var presenter: ChannelOverviewComponentContract.Presenter

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater?.inflate(R.layout.fragment_channel_list_component, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)

        setupRecyclerView()
        refreshSrl.setOnRefreshListener {
            presenter.refresh(false)    // manually initiated refresh should never emit cached data
        }

        presenter.setup()
    }

    override fun onResume() {
        super.onResume()
        presenter.refresh(false)
    }

    override fun showProgress(show: Boolean) {
        progressFl.setVisible(show)
        refreshSrl.setVisible(!show)
        if (!show) {
            refreshSrl.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        channelRv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        channelRv.adapter = ChannelAdapter()
    }

    override fun showChannelOpening(channel: Channel) {
        //storebox channels id 1 - 3
        //ekey channels id 101 - 103

        activity.Starter().activity(ChannelContentActivity::class.java).putExtra(Channel::class.java.simpleName, channel).start()


        //addFragmentOnTop(R.id.containerFl, ChannelOpeningComponentFragment().putArg(Channel::class.java.simpleName, channel) as BaseFragment, false)

        /*
        when (channel.getType()) {
            "channel"  -> {
                //activity.Starter().activity(ChannelContentActivity::class.java).putExtra(Channel::class.java.simpleName, channel).start()
            }
            "storebox" -> {
                // TODO SWITCH BACK TO NORMAL ONCE API IS WORKING
                //activity.Starter().activity(StoreboxContentActivity::class.java).putExtra(Channel::class.java.simpleName, channel).start()
            }
            "ekey"     -> {
                if (channel.installed) {
                    //todo should check if we have mastervault then go straight to activity
                    //activity.Starter().activity(EkeyContentActivity::class.java).putExtra(Channel::class.java.simpleName, channel).start()
                } else {
                    //todo api call to get vault
                    //todo should maybe move EkeyPinComponentFragment to its own activity ?
                    //activity.Starter().activity(EkeyContentActivity::class.java).putExtra(Channel::class.java.simpleName, channel).putExtra("showPin",true).start()
                }
            }
        }
        */
    }

    inner class ChannelAdapter : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

        inner class ChannelViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
            val base = root
            //header
            val headerTv = root.findViewById<TextView>(R.id.headerTv)

            //cards
            val cardContainerCv = root.findViewById<CardView>(R.id.cardContainerCv)
            val backgroundColorV = root.findViewById<View>(R.id.backgroundColorV)
            val backgroundIv = root.findViewById<ImageView>(R.id.backgroundIv)
//            val backgroundOverlayV = root.findViewById<View>(R.id.backgroundOverlayV)
            val headlineTv = root.findViewById<TextView>(R.id.headlineTv)
            val logoIv = root.findViewById<ImageView>(R.id.logoIv)
            val nameTv = root.findViewById<TextView>(R.id.nameTv)
            val openActionTv = root.findViewById<TextView>(R.id.openActionTv)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
            val v = LayoutInflater.from(context).inflate(
                    R.layout.viewholder_channel_cards,
                    parent,
                    false
            )
            return  ChannelViewHolder(v)
        }

        override fun getItemCount(): Int {
            return cards.size
        }

        override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
            var currentChannel = cards[position]

            if (currentChannel.id == -1) {
                holder.headerTv?.visibility = View.VISIBLE
                holder.cardContainerCv?.visibility = View.GONE
                holder.headerTv?.text = Translation.channels.channelsHeader
            } else {
                holder.headerTv?.visibility = View.GONE
                holder.cardContainerCv?.visibility = View.VISIBLE


                holder.backgroundColorV?.background?.setTint(currentChannel.background.color)
                holder.backgroundIv?.let {
                    val requestOptions = RequestOptions()
                            .transform(RoundedCorners(15))

                    Glide.with(context)
                            .load(currentChannel.image?.url)
                            .apply(requestOptions)
                            .into(it)
                }

                if (currentChannel.logo != null) {
                    holder.logoIv?.let {
                        Glide.with(context).load(currentChannel.logo?.url).into(it)
                    }
                }

                holder.headlineTv?.text = currentChannel.payoff

                holder.nameTv?.text = currentChannel.name

                if (currentChannel.installed) {
                    holder.openActionTv?.text = Translation.channels.open
                } else {
                    holder.openActionTv?.text = Translation.channels.install
                }

                holder
                        .cardContainerCv
                        ?.setOnClickListener({ v ->
                                                 onCardContainerClicked(v, currentChannel)
                                             })
            }

            holder.root.invalidate()
        }

        // TODO make this animation go down on down press and stay down till the user lets go
        // instead of just playing animation
        private fun onCardContainerClicked(v: View, currentCard: Channel) {
            v.animate()
                    .scaleX(1.05f)
                    .scaleY(1.05f)
                    .setDuration(150)
                    .setInterpolator(CycleInterpolator(0.5f))
                    .setListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(p0: Animator?) {
                        }

                        override fun onAnimationEnd(p0: Animator?) {
                            presenter.openChannel(currentCard)
                        }

                        override fun onAnimationCancel(p0: Animator?) {
                        }

                        override fun onAnimationStart(p0: Animator?) {
                        }
                    })
                    .start()
        }
    }


    override fun showChannels(channels: List<Channel>) {

        //adding header card added to the top of the list
        cards.clear()
        cards.add(Channel(-1, "", "", null, null, null, null, ChannelColor(), null, false, null))
        //addings channels
        for (channel in channels) {
            cards.add(channel)
        }
        channelRv.adapter.notifyDataSetChanged()
    }

}