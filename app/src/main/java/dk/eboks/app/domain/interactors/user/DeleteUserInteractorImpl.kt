package dk.eboks.app.domain.interactors.user

import dk.eboks.app.domain.managers.UserManager
import dk.eboks.app.domain.models.Translation
import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.util.exceptionToViewError
import dk.eboks.app.util.guard
import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.interactor.BaseInteractor

/**
 * Created by bison on 24-06-2017.
 */
class DeleteUserInteractorImpl(executor: Executor, val userManager: UserManager) : BaseInteractor(executor), DeleteUserInteractor {
    override var output : DeleteUserInteractor.Output? = null
    override var input : DeleteUserInteractor.Input? = null

    override fun execute() {
        // we don't use input in this example but we could:
        try {
            input?.user?.let { user->
                userManager.remove(user)
                runOnUIThread {
                    output?.onDeleteUser(user)
                }
            }.guard {
                runOnUIThread {
                    output?.onDeleteUserError(ViewError(Translation.error.genericStorageTitle, Translation.error.genericStorageMessage))
                }
            }

        } catch (t: Throwable) {
            runOnUIThread {
                output?.onDeleteUserError(exceptionToViewError(t))
            }
        }
    }
}