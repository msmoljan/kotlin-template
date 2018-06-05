package dk.eboks.app.presentation.ui.components.channels.content.ekey.detail

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import dk.eboks.app.R
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.channel.ekey.Ekey
import dk.eboks.app.domain.models.channel.ekey.Login
import dk.eboks.app.domain.models.channel.ekey.Note
import dk.eboks.app.domain.models.channel.ekey.Pin
import dk.eboks.app.presentation.base.BaseFragment
import dk.eboks.app.presentation.ui.components.channels.content.ekey.EkeyItem
import kotlinx.android.synthetic.main.include_toolbar.*
import dk.eboks.app.presentation.ui.components.channels.settings.ChannelSettingsComponentFragment
import dk.eboks.app.util.guard
import kotlinx.android.synthetic.main.fragment_channel_ekey_detail.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by bison on 09-02-2018.
 */
class EkeyDetailComponentFragment : BaseFragment(), EkeyDetailComponentContract.View {

    var category: EkeyDetailMode? = null
    var editKey: Ekey? = null
    var hidePassword: Boolean = false

    @Inject
    lateinit var presenter: EkeyDetailComponentContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_channel_ekey_detail, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)

        arguments?.let { args ->
            category = args.getSerializable("category") as EkeyDetailMode
            if (args.containsKey("login")) {
                editKey = args.get("login") as Login
            }

            if (args.containsKey("pin")) {
                editKey = args.get("pin") as Pin
            }

            if (args.containsKey("note")) {
                editKey = args.get("note") as Note
            }
        }

        pinShowPasswordIb.setOnClickListener {
            showPassword()
        }

        loginShowPasswordIb.setOnClickListener {
            showPassword()
        }
        showPassword()
        setupTopBar()
        setupInputfields()
    }

    private fun setupInputfields() {
        pinShowPasswordIb.visibility = View.GONE
        loginShowPasswordIb.visibility = View.GONE
        pinTil.visibility = View.GONE
        cardholderTil.visibility = View.GONE
        usernameTil.visibility = View.GONE
        passwordTil.visibility = View.GONE

        when (category) {
            EkeyDetailMode.LOGIN -> {
                usernameTil.visibility = View.VISIBLE
                passwordTil.visibility = View.VISIBLE

                editKey?.let {
                    if (it is Login) {
                        nameEt.setText(it.name)
                        usernameEt.setText(it.username)
                        passwordEt.setText(it.password)
                        noteEt.setText(it.note)
                        loginShowPasswordIb.visibility = View.VISIBLE
                    }
                }
            }
            EkeyDetailMode.PIN -> {
                pinTil.visibility = View.VISIBLE
                cardholderTil.visibility = View.VISIBLE
                nameTil.hint = Translation.ekey.inputHintCardtype

                editKey?.let {
                    if (it is Pin) {
                        nameEt.setText(it.name)
                        pinEt.setText(it.pin)
                        noteEt.setText(it.note)
                        pinShowPasswordIb.visibility = View.VISIBLE
                    }
                }
            }
            EkeyDetailMode.NOTE -> {
                editKey?.let {
                    if (it is Note) {
                        nameEt.setText(it.name)
                        noteEt.setText(it.note)
                    }
                }
            }
        }
    }

    private fun showPassword(){
        hidePassword = !hidePassword
        if (hidePassword) {
            passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()
            pinEt.transformationMethod = PasswordTransformationMethod.getInstance()
            pinShowPasswordIb.isSelected = false
            loginShowPasswordIb.isSelected = false
        } else {
            passwordEt.transformationMethod = null
            pinEt.transformationMethod = null
            pinShowPasswordIb.isSelected = true
            loginShowPasswordIb.isSelected = true

        }
    }

    private fun setupTopBar() {
        getBaseActivity()?.mainTb?.menu?.clear()
        var item = ""
        when (category) {
            EkeyDetailMode.LOGIN -> {
                item = Translation.ekey.addItemLogin
            }
            EkeyDetailMode.PIN -> {
                item = Translation.ekey.addPinCode
            }
            EkeyDetailMode.NOTE -> {
                item = Translation.ekey.addItemNote
            }
        }

        editKey.let {
            getBaseActivity()?.mainTb?.title = item
        }.guard {
            getBaseActivity()?.mainTb?.title = Translation.ekey.saveTopbar.replace("[item]", item)
        }

        getBaseActivity()?.mainTb?.setNavigationIcon(R.drawable.icon_48_chevron_left_red_navigationbar)
        getBaseActivity()?.mainTb?.setNavigationOnClickListener {
            getBaseActivity()?.onBackPressed()
        }

        val menuSearch = getBaseActivity()?.mainTb?.menu?.add(Translation.defaultSection.save.toUpperCase())
        menuSearch?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menuSearch?.setOnMenuItemClickListener { item: MenuItem ->
            //todo save clicked
            var temp = "_Save clicked"
            Timber.e(temp)
            true
        }
    }

}