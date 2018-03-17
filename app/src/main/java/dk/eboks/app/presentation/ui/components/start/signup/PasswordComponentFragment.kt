package dk.eboks.app.presentation.ui.components.start.signup

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import dk.eboks.app.R
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.presentation.base.BaseFragment
import dk.eboks.app.presentation.ui.screens.start.StartActivity
import kotlinx.android.synthetic.main.fragment_signup_password_component.*
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by bison on 09-02-2018.
 */
class PasswordComponentFragment : BaseFragment(), SignupComponentContract.PasswordView {

    @Inject
    lateinit var presenter: SignupComponentContract.Presenter

    var passwordValid = false
    var repeatPasswordValid = false
    var mHandler = Handler()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater?.inflate(R.layout.fragment_signup_password_component, container, false)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)
        continueBtn.setOnClickListener { onContinueClicked() }
        getBaseActivity()?.setToolbar(R.drawable.red_navigationbar, Translation.signup.title, null, {
            fragmentManager.popBackStack()
        })

        setupPasswordListeners()
        setupRepeatPasswordListener()
    }

    private fun setupRepeatPasswordListener() {
        repeatPasswordEt.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                comparePasswords()
                setErrorMessages()
            }
        }

        repeatPasswordEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(repeatPassword: Editable?) {
                mHandler.removeCallbacksAndMessages(null)
                repeatPasswordTil.error = null
                comparePasswords()
                mHandler?.postDelayed({
                    setErrorMessages()
                }, 1200)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setErrorMessages() {
        if (!passwordEt.text.isNullOrBlank() && !passwordValid) {
        passwordTil.error = Translation.signup.invalidPassword
        } else {
            passwordTil.error = null
        }

        if (!repeatPasswordEt.text.isNullOrBlank() && !repeatPasswordValid){
            repeatPasswordTil.error = Translation.signup.invalidPasswordMatch
        } else {
            repeatPasswordTil.error = null
        }
    }

    private fun setupPasswordListeners() {
        passwordEt.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                comparePasswords()
                setErrorMessages()
            }
        }

        passwordEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(password: Editable?) {
                mHandler.removeCallbacksAndMessages(null)
                passwordTil.error = null
                comparePasswords()
                mHandler?.postDelayed({
                    setErrorMessages()
                }, 1200)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun comparePasswords() {
        passwordValid = (isValidPassword(passwordEt.text.toString().trim()))
        repeatPasswordValid = (passwordEt.text.toString().trim() == repeatPasswordEt.text.toString().trim())
        continueBtn.isEnabled = (passwordValid && repeatPasswordValid)
    }

    fun isValidPassword(password: CharSequence): Boolean {
        if (password.isNotBlank()) {
            return true
        }
        return false
    }

    override fun setupTranslations() {
        headerTv.text = Translation.signup.passwordHeader
        detailTv.text = Translation.signup.passwordDetail
        continueBtn.text = Translation.signup.continueButton
        passwordTil.hint = Translation.signup.passwordHint
        repeatPasswordTil.hint = Translation.signup.repeatPasswordHint
    }

    override fun showError() {

    }

    override fun showProgress(show: Boolean) {
        content.visibility = if (show) View.GONE else View.VISIBLE
        progress.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun onContinueClicked() {
        //(activity as StartActivity).showLogo(false)
        showProgress(true)
        content.postDelayed({
            showProgress(false)
            getBaseActivity()?.replaceFragment(R.id.containerFl, SignupVerificationComponentFragment(), true)
        }, 1000)
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}