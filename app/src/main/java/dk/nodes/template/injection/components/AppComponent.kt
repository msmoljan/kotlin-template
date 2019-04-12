package dk.nodes.template.injection.components

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dk.nodes.template.App
import dk.nodes.template.injection.modules.AppModule
import dk.nodes.template.injection.modules.ExecutorModule
import dk.nodes.template.injection.modules.InteractorModule
import dk.nodes.template.injection.modules.RestModule
import dk.nodes.template.injection.modules.RestRepositoryBinding
import dk.nodes.template.injection.modules.StorageModule
import dk.nodes.template.presentation.injection.ViewModelBuilder
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelBuilder::class,
        AppModule::class,
        ExecutorModule::class,
        InteractorModule::class,
        RestModule::class,
        RestRepositoryBinding::class,
        StorageModule.StorageBindingModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}