package dk.nodes.template.nstack

import android.app.Application
import dk.nodes.nstack.kotlin.NStack
import dk.nodes.template.app_context.AppInitializer

class NStackAppInitializer() : AppInitializer {

    override fun init(app: Application) {
        NStack.translationClass = Translation::class.java
        NStack.init(app, BuildConfig.DEBUG)

        if (BuildConfig.DEBUG) {
            NStack.enableLiveEdit(app)
            // Timber.plant(Timber.DebugTree())
        }
    }
}