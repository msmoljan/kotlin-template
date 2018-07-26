package dk.eboks.app.domain.interactors.message

import dk.eboks.app.domain.models.local.ViewError
import dk.nodes.arch.domain.interactor.Interactor

/**
 * Created by bison on 01/02/18.
 */
interface UploadFileInteractor : Interactor {
    var output : Output?
    var input : Input?


    data class Input(var folderId : Int, var filename : String, var uriString : String, var mimetype : String)

    interface Output {
        fun onUploadFileComplete()
        fun onUploadFileProgress(pct : Float)
        fun onUploadFileError(error : ViewError)
    }
}