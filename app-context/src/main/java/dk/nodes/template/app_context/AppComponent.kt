package dk.nodes.template.app_context

import android.app.Application
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * We use a subcomponent for the app since the main component is in the dep. injection module
 */
@Subcomponent
interface AppComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application?): Builder

        fun build(): AppComponent
    }

    fun inject(application: App)
}