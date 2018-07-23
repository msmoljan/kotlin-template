package dk.eboks.app.presentation.ui.debug.components

import dk.eboks.app.domain.config.Config
import dk.eboks.app.domain.managers.AppStateManager
import dk.nodes.arch.presentation.base.BasePresenterImpl
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by bison on 20-05-2017.
 */
class DebugOptionsComponentPresenter @Inject constructor(val appState: AppStateManager) : DebugOptionsComponentContract.Presenter, BasePresenterImpl<DebugOptionsComponentContract.View>() {

    init {
    }

    override fun setup() {
        setupConfigurationView()
        runAction { v->v.showEnvironmentSpinner(Config.currentMode.environments, Config.currentMode.environment) }
    }

    override fun setConfig(name : String)
    {
        Config.changeConfig(name)
        Timber.e("Config changed: new current configuration is ${Config.getCurrentConfigName()}")
        runAction { v->v.showEnvironmentSpinner(Config.currentMode.environments, Config.currentMode.environment) }
    }

    override fun setEnvironment(name: String) {
        Config.changeEnvironment(name)
        Timber.e("Environment changed to ${name}")
    }

    private fun setupConfigurationView()
    {
        when(Config.getCurrentConfigName())
        {
            "danish" -> {
                runAction { v->v.showCountrySpinner(0) }
            }
            "swedish" -> {
                runAction { v->v.showCountrySpinner(2) }
            }
            "norwegian" -> {
                runAction { v->v.showCountrySpinner(1) }
            }
            else -> {}
        }
    }

}