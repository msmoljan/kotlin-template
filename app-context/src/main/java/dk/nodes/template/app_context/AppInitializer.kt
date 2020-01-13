package dk.nodes.template.app_context

import android.app.Application

interface AppInitializer {
    fun init(app: Application)
}