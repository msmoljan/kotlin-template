package dk.eboks.app.presentation.ui.message.screens.reply

import android.os.Bundle
import dk.eboks.app.R
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.formreply.FormInput
import dk.eboks.app.domain.models.formreply.FormInputType
import dk.eboks.app.domain.models.message.Message
import dk.eboks.app.mail.presentation.ui.message.screens.reply.ReplyFormContract
import dk.eboks.app.mail.presentation.ui.message.screens.reply.ReplyFormInput
import dk.eboks.app.presentation.base.BaseActivity
import dk.eboks.app.util.guard
import androidx.core.view.isInvisible
import androidx.core.view.children
import dk.nodes.nstack.kotlin.util.OnLanguageChangedListener
import kotlinx.android.synthetic.main.activity_reply_form.*
import kotlinx.android.synthetic.main.include_toolbar.*
import timber.log.Timber
import java.util.Locale
import java.util.Observer
import javax.inject.Inject

class ReplyFormActivity : BaseActivity(), ReplyFormContract.View, OnLanguageChangedListener {
    @Inject lateinit var presenter: ReplyFormContract.Presenter

    // observer without rx, how is teh possible?
    private val inputObserver = Observer { observable, newval ->
        Timber.d("Input observer firing! $observable")
        submitBtn?.isEnabled = allInputsValidate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reply_form)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)
        setupTopBar(Translation.reply.title)

        // deserialize or message and hand it to the presenter
        intent?.extras?.getParcelable<Message>(Message::class.java.simpleName)
            ?.let(presenter::setup)
            .guard { finish() } // finish if we didn't get a message

        submitBtn.setOnClickListener {
            presenter.submit(
                replyForms().map { it.formInput }.toList()
            )
        }
    }

    private fun replyForms() = formInputLl
            .children
            .map { it.tag }
            .filterIsInstance<ReplyFormInput>()

    private fun setupTopBar(txt: String) {
        mainTb.setNavigationIcon(R.drawable.ic_red_close)
        mainTb.title = txt
        mainTb.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        // iterate through form inputs and let them register listeners
        replyForms().forEach { input -> input.onResume() }
    }

    override fun onPause() {
        // iterate through form inputs and let them deregister listeners
        replyForms().forEach { input -> input.onPause() }
        super.onPause()
    }

    private fun allInputsValidate(): Boolean {
        if (formInputLl.childCount == 0)
            return false
        replyForms().forEach { input ->
            if (!input.isValid) {
                // Timber.e("${input.formInput.label} shit does not validate")
                return false
            }
        }
        return true // alles sehr gut
    }

    override fun onLanguageChanged(locale: Locale) {
        mainTb.title = Translation.reply.title
    }

    override fun showProgress(show: Boolean) {
        progressFl.isInvisible = !show
    }

    override fun clearForm() {
        formInputLl.removeAllViews()
    }

    override fun showFormInput(input: FormInput) {
        // Timber.e("showing form input $input")
        when (input.type) {
            FormInputType.DESCRIPTION -> {
                val fi = DescriptionFormInput(input, inflator, mainHandler)
                fi.addObserver(inputObserver)
                fi.addViewGroup(formInputLl)
            }
            FormInputType.LINK -> {
                val fi = LinkFormInput(input, inflator, mainHandler)
                fi.addObserver(inputObserver)
                fi.addViewGroup(formInputLl)
            }
            FormInputType.TEXT -> {
                val fi = TextFormInput(input, inflator, mainHandler)
                fi.addObserver(inputObserver)
                fi.addViewGroup(formInputLl)
            }
            FormInputType.TEXTAREA -> {
                val fi = TextFormInput(input, inflator, mainHandler, multiline = true)
                fi.addObserver(inputObserver)
                fi.addViewGroup(formInputLl)
            }
            FormInputType.NUMBER -> {
                val fi = NumberFormInput(input, inflator, mainHandler)
                fi.addObserver(inputObserver)
                fi.addViewGroup(formInputLl)
            }
            FormInputType.DROPDOWN -> {
                val fi = DropdownFormInput(input, inflator, mainHandler)
                fi.addObserver(inputObserver)
                fi.addViewGroup(formInputLl)
            }
            FormInputType.DATE -> {
                val fi = DateFormInput(input, inflator, mainHandler)
                fi.addObserver(inputObserver)
                fi.addViewGroup(formInputLl)
            }
            FormInputType.DATETIME -> {
                val fi = DateFormInput(input, inflator, mainHandler, isDateTime = true)
                fi.addObserver(inputObserver)
                fi.addViewGroup(formInputLl)
            }
            FormInputType.RADIOBOX -> {
                val fi = RadioBoxFormInput(input, inflator, mainHandler)
                fi.addObserver(inputObserver)
                fi.addViewGroup(formInputLl)
            }
            FormInputType.LIST -> {
                val fi = CheckBoxFormInput(input, inflator, mainHandler)
                fi.addObserver(inputObserver)
                fi.addViewGroup(formInputLl)
            }
            else -> {
            }
        }
    }
}
