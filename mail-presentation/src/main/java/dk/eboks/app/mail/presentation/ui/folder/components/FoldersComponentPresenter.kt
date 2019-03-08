package dk.eboks.app.mail.presentation.ui.folder.components

import dk.eboks.app.domain.managers.AppStateManager
import dk.eboks.app.domain.models.folder.Folder
import dk.eboks.app.domain.models.folder.FolderMode
import dk.eboks.app.domain.models.local.ViewError
import dk.eboks.app.mail.domain.interactors.folder.GetFoldersInteractor
import dk.eboks.app.mail.domain.interactors.folder.OpenFolderInteractor
import dk.nodes.arch.presentation.base.BasePresenterImpl
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by bison on 20-05-2017.
 */
internal class FoldersComponentPresenter @Inject constructor(
    private val appState: AppStateManager,
    private val getFoldersInteractor: GetFoldersInteractor,
    private val openFolderInteractor: OpenFolderInteractor
) :
    FoldersComponentContract.Presenter,
    BasePresenterImpl<FoldersComponentContract.View>(),
    GetFoldersInteractor.Output,
    OpenFolderInteractor.Output {

    private var pickermode: FolderMode = FolderMode.NORMAL

    init {

        openFolderInteractor.output = this
        getFoldersInteractor.output = this
        view {
            showProgress(true)
            setUser(appState.state?.currentUser)
            pickermode = getModeType()
            refresh()
        }
    }

    override fun refresh() {
        Timber.d("${view?.isSharedUserActive}")
        val userId =
            if (view?.isSharedUserActive == false) null else appState.state?.impersoniateUser?.userId
        getFoldersInteractor.input = GetFoldersInteractor.Input(false, pickermode, userId)
        getFoldersInteractor.run()
    }

    override fun openFolder(folder: Folder) {
        openFolderInteractor.input = OpenFolderInteractor.Input(folder)
        openFolderInteractor.run()
    }

    override fun onGetFolders(folders: List<Folder>) {
        view {
            showProgress(false)
            showUserFolders(folders)
            showRefreshProgress(false)
        }
    }

    override fun onGetSystemFolders(folders: List<Folder>) {
        view { showSystemFolders(folders) }
    }

    override fun onGetFoldersError(error: ViewError) {
        view {
            showProgress(false)
            showRefreshProgress(false)
            showErrorDialog(error)
        }
    }

    override fun onOpenFolderDone() {
    }

    override fun onOpenFolderError(error: ViewError) {
        view { showErrorDialog(error) }
    }
}