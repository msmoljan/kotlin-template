package dk.eboks.app.presentation.ui.screens.channels.content.ekey

import android.os.Bundle
import dk.eboks.app.R
import dk.eboks.app.presentation.base.BaseActivity
import dk.eboks.app.presentation.ui.components.channels.content.ekey.EkeyComponentFragment
import dk.eboks.app.presentation.ui.components.channels.content.ekey.pin.EkeyPinComponentFragment
import javax.inject.Inject

class EkeyContentActivity : BaseActivity(), EkeyContentContract.View {
    @Inject lateinit var presenter: EkeyContentContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(dk.eboks.app.R.layout.activity_ekey_content)
        component.inject(this)
        presenter.onViewCreated(this, lifecycle)

        if (intent.getBooleanExtra("showPin",false)){
            addFragmentOnTop(R.id.content,EkeyPinComponentFragment(),false)
//            setRootFragment(R.id.content, EkeyPinComponentFragment())
        } else {
            setRootFragment(R.id.content, EkeyComponentFragment())
        }
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                if (!isDestroyed)
                    finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            if (!isDestroyed)
                finish()
        }
    }


}