package dk.nodes.template.dependency_injection.components

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dk.nodes.template.app_context.App
import dk.nodes.template.dependency_injection.modules.AppModule
import dk.nodes.template.dependency_injection.modules.InteractorModule
import dk.nodes.template.dependency_injection.modules.RestModule
import dk.nodes.template.dependency_injection.modules.RestRepositoryBinding
import dk.nodes.template.presentation.injection.PresentationModule
import dk.nodes.template.presentation.injection.ViewModelBuilder
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelBuilder::class,
        PresentationModule::class,
        AppModule::class,
        InteractorModule::class,
        RestModule::class,
        RestRepositoryBinding::class
    ]
)
@Singleton
interface GlobalComponent : AndroidInjector<App> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<App>
}