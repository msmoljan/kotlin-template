package dk.eboks.app.domain.interactors.user

import dk.eboks.app.domain.repositories.UserRepository
import dk.eboks.app.util.exceptionToViewError
import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.interactor.BaseInteractor
import javax.inject.Inject

class VerifyEmailInteractorImpl @Inject constructor(
    executor: Executor,
    private val userRestRepo: UserRepository
) : BaseInteractor(executor), VerifyEmailInteractor {
    override var output: VerifyEmailInteractor.Output? = null
    override var input: VerifyEmailInteractor.Input? = null

    override fun execute() {
        try {

            input?.mail?.let {
                userRestRepo.verifyEmail(it)
            }
            runOnUIThread {
                output?.onVerifyMail()
            }
        } catch (t: Throwable) {
            runOnUIThread {
                output?.onVerifyMailError(exceptionToViewError(t, shouldDisplay = false))
            }
        }
    }
}