package dk.nodes.template.dependency_injection.modules

import dagger.Module
import dk.nodes.template.app_context.AppComponent

@Module(subcomponents = [AppComponent::class])
class AppModule