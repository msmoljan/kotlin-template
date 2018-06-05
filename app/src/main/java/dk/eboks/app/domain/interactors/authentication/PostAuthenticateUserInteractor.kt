package dk.eboks.app.domain.interactors.authentication

import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.domain.models.login.AccessToken
import dk.eboks.app.domain.models.login.User
import dk.nodes.arch.domain.interactor.Interactor

/**
 * Created by Christian on 4/20/2018.
 * @author   Christian
 * @since    4/20/2018.
 */
interface PostAuthenticateUserInteractor : Interactor
{
    var input : Input?
    var output : Output?

    data class Input(val user: User, val password: String, val activationCode: String?)

    interface Output {
        /** All good! */
        fun onAuthenticationsSuccess(user: User, response: AccessToken)
        /** Auth-fail */
        fun onAuthenticationsDenied(error : ViewError)
        /** Network-fail */
        fun onAuthenticationsError(error : ViewError)
    }
}