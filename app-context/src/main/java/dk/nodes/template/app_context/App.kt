package dk.nodes.template.app_context

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import javax.inject.Provider

class App : Application(), HasAndroidInjector {

    companion object {
        const val API_URL = BuildConfig.API_URL
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var appComponentProvider: Provider<AppComponent.Builder>

    @Inject
    lateinit var initializers: Iterable<AppInitializer>

    override fun onCreate() {
        super.onCreate()

        appComponentProvider
            .get()
            .application(this)
            .build()
            .inject(this)

        initializers.forEach { initializer -> initializer.init(this) }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        MultiDex.install(this)
    }

    override fun androidInjector(): DispatchingAndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}