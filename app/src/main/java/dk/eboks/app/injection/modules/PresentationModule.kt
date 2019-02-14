package dk.eboks.app.injection.modules

import dagger.Module
import dagger.Provides
import dk.eboks.app.domain.interactors.BootstrapInteractor
import dk.eboks.app.domain.interactors.GetCategoriesInteractor
import dk.eboks.app.domain.interactors.authentication.CheckRSAKeyPresenceInteractor
import dk.eboks.app.domain.interactors.authentication.LoginInteractor
import dk.eboks.app.domain.interactors.authentication.MergeAndImpersonateInteractor
import dk.eboks.app.domain.interactors.authentication.ResetPasswordInteractor
import dk.eboks.app.domain.interactors.authentication.SetCurrentUserInteractor
import dk.eboks.app.domain.interactors.authentication.TestLoginInteractor
import dk.eboks.app.domain.interactors.authentication.TransformTokenInteractor
import dk.eboks.app.domain.interactors.authentication.VerifyProfileInteractor
import dk.eboks.app.domain.interactors.channel.GetChannelContentLinkInteractor
import dk.eboks.app.domain.interactors.channel.GetChannelInteractor
import dk.eboks.app.domain.interactors.channel.GetChannelsInteractor
import dk.eboks.app.domain.interactors.channel.InstallChannelInteractor
import dk.eboks.app.domain.interactors.channel.UninstallChannelInteractor
import dk.eboks.app.domain.interactors.encryption.DecryptUserLoginInfoInteractor
import dk.eboks.app.domain.interactors.encryption.EncryptUserLoginInfoInteractor
import dk.eboks.app.domain.interactors.folder.CreateFolderInteractor
import dk.eboks.app.domain.interactors.folder.DeleteFolderInteractor
import dk.eboks.app.domain.interactors.folder.EditFolderInteractor
import dk.eboks.app.domain.interactors.folder.GetFoldersInteractor
import dk.eboks.app.domain.interactors.folder.OpenFolderInteractor
import dk.eboks.app.domain.interactors.message.GetLatestUploadsInteractor
import dk.eboks.app.domain.interactors.message.GetMessagesInteractor
import dk.eboks.app.domain.interactors.message.GetReplyFormInteractor
import dk.eboks.app.domain.interactors.message.GetSignLinkInteractor
import dk.eboks.app.domain.interactors.message.GetStorageInteractor
import dk.eboks.app.domain.interactors.message.OpenAttachmentInteractor
import dk.eboks.app.domain.interactors.message.OpenMessageInteractor
import dk.eboks.app.domain.interactors.message.SaveAttachmentInteractor
import dk.eboks.app.domain.interactors.message.SubmitReplyFormInteractor
import dk.eboks.app.domain.interactors.message.UploadFileInteractor
import dk.eboks.app.domain.interactors.message.messageoperations.DeleteMessagesInteractor
import dk.eboks.app.domain.interactors.message.messageoperations.MoveMessagesInteractor
import dk.eboks.app.domain.interactors.message.messageoperations.UpdateMessageInteractor
import dk.eboks.app.domain.interactors.sender.GetSegmentInteractor
import dk.eboks.app.domain.interactors.sender.GetSenderCategoriesInteractor
import dk.eboks.app.domain.interactors.sender.GetSenderDetailInteractor
import dk.eboks.app.domain.interactors.sender.GetSendersInteractor
import dk.eboks.app.domain.interactors.sender.register.GetPendingInteractor
import dk.eboks.app.domain.interactors.sender.register.GetRegistrationsInteractor
import dk.eboks.app.domain.interactors.sender.register.RegisterInteractor
import dk.eboks.app.domain.interactors.sender.register.UnRegisterInteractor
import dk.eboks.app.domain.interactors.signup.CheckSignupMailInteractor
import dk.eboks.app.domain.interactors.storebox.ConfirmStoreboxInteractor
import dk.eboks.app.domain.interactors.storebox.CreateStoreboxInteractor
import dk.eboks.app.domain.interactors.storebox.DeleteStoreboxAccountLinkInteractor
import dk.eboks.app.domain.interactors.storebox.DeleteStoreboxCreditCardInteractor
import dk.eboks.app.domain.interactors.storebox.DeleteStoreboxReceiptInteractor
import dk.eboks.app.domain.interactors.storebox.GetStoreboxCardLinkInteractor
import dk.eboks.app.domain.interactors.storebox.GetStoreboxCreditCardsInteractor
import dk.eboks.app.domain.interactors.storebox.GetStoreboxProfileInteractor
import dk.eboks.app.domain.interactors.storebox.GetStoreboxReceiptInteractor
import dk.eboks.app.domain.interactors.storebox.GetStoreboxReceiptsInteractor
import dk.eboks.app.domain.interactors.storebox.LinkStoreboxInteractor
import dk.eboks.app.domain.interactors.storebox.PutStoreboxProfileInteractor
import dk.eboks.app.domain.interactors.storebox.SaveReceiptInteractor
import dk.eboks.app.domain.interactors.storebox.ShareReceiptInteractor
import dk.eboks.app.domain.interactors.storebox.UpdateStoreboxFlagsInteractor
import dk.eboks.app.domain.interactors.user.CheckSsnExistsInteractor
import dk.eboks.app.domain.interactors.user.ConfirmPhoneInteractor
import dk.eboks.app.domain.interactors.user.CreateDebugUserInteractorImpl
import dk.eboks.app.domain.interactors.user.CreateUserInteractor
import dk.eboks.app.domain.interactors.user.DeleteUserInteractor
import dk.eboks.app.domain.interactors.user.GetUserProfileInteractor
import dk.eboks.app.domain.interactors.user.GetUsersInteractor
import dk.eboks.app.domain.interactors.user.SaveUserInteractor
import dk.eboks.app.domain.interactors.user.SaveUserSettingsInteractor
import dk.eboks.app.domain.interactors.user.UpdateUserInteractor
import dk.eboks.app.domain.interactors.user.VerifyEmailInteractor
import dk.eboks.app.domain.interactors.user.VerifyPhoneInteractor
import dk.eboks.app.domain.managers.AppStateManager
import dk.eboks.app.domain.managers.PrefManager
import dk.eboks.app.domain.managers.UserSettingsManager
import dk.eboks.app.domain.models.login.LoginState
import dk.eboks.app.pasta.activity.PastaContract
import dk.eboks.app.pasta.activity.PastaPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.EkeyComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.EkeyComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.additem.EkeyAddItemComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.additem.EkeyAddItemComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.detail.EkeyDetailComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.detail.EkeyDetailComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.open.EkeyOpenItemComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.open.EkeyOpenItemComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.ekey.pin.EkeyPinComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.ekey.pin.EkeyPinComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.storebox.content.ChannelContentStoreboxComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.storebox.content.ChannelContentStoreboxComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.storebox.detail.ChannelContentStoreboxDetailComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.storebox.detail.ChannelContentStoreboxDetailComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.content.web.ChannelContentComponentContract
import dk.eboks.app.presentation.ui.channels.components.content.web.ChannelContentComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.opening.ChannelOpeningComponentContract
import dk.eboks.app.presentation.ui.channels.components.opening.ChannelOpeningComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.overview.ChannelOverviewComponentContract
import dk.eboks.app.presentation.ui.channels.components.overview.ChannelOverviewComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.requirements.ChannelRequirementsComponentContract
import dk.eboks.app.presentation.ui.channels.components.requirements.ChannelRequirementsComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.settings.ChannelSettingsComponentContract
import dk.eboks.app.presentation.ui.channels.components.settings.ChannelSettingsComponentPresenter
import dk.eboks.app.presentation.ui.channels.components.verification.ChannelVerificationComponentContract
import dk.eboks.app.presentation.ui.channels.components.verification.ChannelVerificationComponentPresenter
import dk.eboks.app.presentation.ui.channels.screens.content.ChannelContentContract
import dk.eboks.app.presentation.ui.channels.screens.content.ChannelContentPresenter
import dk.eboks.app.presentation.ui.channels.screens.content.ekey.EkeyContentContract
import dk.eboks.app.presentation.ui.channels.screens.content.ekey.EkeyContentPresenter
import dk.eboks.app.presentation.ui.channels.screens.content.storebox.ConnectStoreboxContract
import dk.eboks.app.presentation.ui.channels.screens.content.storebox.ConnectStoreboxPresenter
import dk.eboks.app.presentation.ui.channels.screens.overview.ChannelOverviewContract
import dk.eboks.app.presentation.ui.channels.screens.overview.ChannelOverviewPresenter
import dk.eboks.app.presentation.ui.debug.components.DebugOptionsComponentContract
import dk.eboks.app.presentation.ui.debug.components.DebugOptionsComponentPresenter
import dk.eboks.app.presentation.ui.debug.components.DebugUsersComponentContract
import dk.eboks.app.presentation.ui.debug.components.DebugUsersComponentPresenter
import dk.eboks.app.presentation.ui.debug.screens.user.DebugUserContract
import dk.eboks.app.presentation.ui.debug.screens.user.DebugUserPresenter
import dk.eboks.app.presentation.ui.folder.components.FoldersComponentContract
import dk.eboks.app.presentation.ui.folder.components.FoldersComponentPresenter
import dk.eboks.app.presentation.ui.folder.components.newfolder.NewFolderComponentContract
import dk.eboks.app.presentation.ui.folder.components.newfolder.NewFolderComponentPresenter
import dk.eboks.app.presentation.ui.folder.components.selectuser.FolderSelectUserComponentContract
import dk.eboks.app.presentation.ui.folder.components.selectuser.FolderSelectUserComponentPresenter
import dk.eboks.app.presentation.ui.folder.screens.FolderContract
import dk.eboks.app.presentation.ui.folder.screens.FolderPresenter
import dk.eboks.app.presentation.ui.home.components.channelcontrol.ChannelControlComponentContract
import dk.eboks.app.presentation.ui.home.components.channelcontrol.ChannelControlComponentPresenter
import dk.eboks.app.presentation.ui.home.components.folderpreview.FolderPreviewComponentContract
import dk.eboks.app.presentation.ui.home.components.folderpreview.FolderPreviewComponentPresenter
import dk.eboks.app.presentation.ui.login.components.ActivationCodeComponentContract
import dk.eboks.app.presentation.ui.login.components.ActivationCodeComponentPresenter
import dk.eboks.app.presentation.ui.login.components.DeviceActivationComponentContract
import dk.eboks.app.presentation.ui.login.components.DeviceActivationComponentPresenter
import dk.eboks.app.presentation.ui.login.components.ForgotPasswordComponentContract
import dk.eboks.app.presentation.ui.login.components.ForgotPasswordComponentPresenter
import dk.eboks.app.presentation.ui.login.components.ForgotPasswordDoneComponentContract
import dk.eboks.app.presentation.ui.login.components.ForgotPasswordDoneComponentPresenter
import dk.eboks.app.presentation.ui.login.components.LoginComponentContract
import dk.eboks.app.presentation.ui.login.components.LoginComponentPresenter
import dk.eboks.app.presentation.ui.login.components.UserCarouselComponentContract
import dk.eboks.app.presentation.ui.login.components.UserCarouselComponentPresenter
import dk.eboks.app.presentation.ui.login.components.providers.WebLoginContract
import dk.eboks.app.presentation.ui.login.components.providers.WebLoginPresenter
import dk.eboks.app.presentation.ui.login.components.providers.bankidno.BankIdNOComponentPresenter
import dk.eboks.app.presentation.ui.login.components.providers.bankidse.BankIdSEComponentPresenter
import dk.eboks.app.presentation.ui.login.components.providers.idporten.IdPortenComponentPresenter
import dk.eboks.app.presentation.ui.login.components.providers.nemid.NemIdComponentPresenter
import dk.eboks.app.presentation.ui.login.components.verification.VerificationComponentContract
import dk.eboks.app.presentation.ui.login.components.verification.VerificationComponentPresenter
import dk.eboks.app.presentation.ui.login.screens.PopupLoginContract
import dk.eboks.app.presentation.ui.login.screens.PopupLoginPresenter
import dk.eboks.app.presentation.ui.mail.components.foldershortcuts.FolderShortcutsComponentContract
import dk.eboks.app.presentation.ui.mail.components.foldershortcuts.FolderShortcutsComponentPresenter
import dk.eboks.app.presentation.ui.mail.components.maillist.MailListComponentContract
import dk.eboks.app.presentation.ui.mail.components.maillist.MailListComponentPresenter
import dk.eboks.app.presentation.ui.mail.components.sendercarousel.SenderCarouselComponentContract
import dk.eboks.app.presentation.ui.mail.components.sendercarousel.SenderCarouselComponentPresenter
import dk.eboks.app.presentation.ui.mail.screens.list.MailListContract
import dk.eboks.app.presentation.ui.mail.screens.list.MailListPresenter
import dk.eboks.app.presentation.ui.mail.screens.overview.MailOverviewContract
import dk.eboks.app.presentation.ui.mail.screens.overview.MailOverviewPresenter
import dk.eboks.app.presentation.ui.message.components.detail.attachments.AttachmentsComponentContract
import dk.eboks.app.presentation.ui.message.components.detail.attachments.AttachmentsComponentPresenter
import dk.eboks.app.presentation.ui.message.components.detail.document.DocumentComponentContract
import dk.eboks.app.presentation.ui.message.components.detail.document.DocumentComponentPresenter
import dk.eboks.app.presentation.ui.message.components.detail.folderinfo.FolderInfoComponentContract
import dk.eboks.app.presentation.ui.message.components.detail.folderinfo.FolderInfoComponentPresenter
import dk.eboks.app.presentation.ui.message.components.detail.header.HeaderComponentContract
import dk.eboks.app.presentation.ui.message.components.detail.header.HeaderComponentPresenter
import dk.eboks.app.presentation.ui.message.components.detail.notes.NotesComponentContract
import dk.eboks.app.presentation.ui.message.components.detail.notes.NotesComponentPresenter
import dk.eboks.app.presentation.ui.message.components.detail.payment.PaymentComponentContract
import dk.eboks.app.presentation.ui.message.components.detail.payment.PaymentComponentPresenter
import dk.eboks.app.presentation.ui.message.components.detail.reply.ReplyButtonComponentContract
import dk.eboks.app.presentation.ui.message.components.detail.reply.ReplyButtonComponentPresenter
import dk.eboks.app.presentation.ui.message.components.detail.share.ShareComponentContract
import dk.eboks.app.presentation.ui.message.components.detail.share.ShareComponentPresenter
import dk.eboks.app.presentation.ui.message.components.detail.sign.SignButtonComponentContract
import dk.eboks.app.presentation.ui.message.components.detail.sign.SignButtonComponentPresenter
import dk.eboks.app.presentation.ui.message.components.opening.privatesender.PrivateSenderWarningComponentContract
import dk.eboks.app.presentation.ui.message.components.opening.privatesender.PrivateSenderWarningComponentPresenter
import dk.eboks.app.presentation.ui.message.components.opening.promulgation.PromulgationComponentContract
import dk.eboks.app.presentation.ui.message.components.opening.promulgation.PromulgationComponentPresenter
import dk.eboks.app.presentation.ui.message.components.opening.protectedmessage.ProtectedMessageComponentContract
import dk.eboks.app.presentation.ui.message.components.opening.protectedmessage.ProtectedMessageComponentPresenter
import dk.eboks.app.presentation.ui.message.components.opening.quarantine.QuarantineComponentContract
import dk.eboks.app.presentation.ui.message.components.opening.quarantine.QuarantineComponentPresenter
import dk.eboks.app.presentation.ui.message.components.opening.recalled.RecalledComponentContract
import dk.eboks.app.presentation.ui.message.components.opening.recalled.RecalledComponentPresenter
import dk.eboks.app.presentation.ui.message.components.opening.receipt.OpeningReceiptComponentContract
import dk.eboks.app.presentation.ui.message.components.opening.receipt.OpeningReceiptComponentPresenter
import dk.eboks.app.presentation.ui.message.components.viewers.html.HtmlViewComponentContract
import dk.eboks.app.presentation.ui.message.components.viewers.html.HtmlViewComponentPresenter
import dk.eboks.app.presentation.ui.message.components.viewers.image.ImageViewComponentContract
import dk.eboks.app.presentation.ui.message.components.viewers.image.ImageViewComponentPresenter
import dk.eboks.app.presentation.ui.message.components.viewers.pdf.PdfViewComponentContract
import dk.eboks.app.presentation.ui.message.components.viewers.pdf.PdfViewComponentPresenter
import dk.eboks.app.presentation.ui.message.components.viewers.text.TextViewComponentContract
import dk.eboks.app.presentation.ui.message.components.viewers.text.TextViewComponentPresenter
import dk.eboks.app.presentation.ui.message.screens.MessageContract
import dk.eboks.app.presentation.ui.message.screens.MessagePresenter
import dk.eboks.app.presentation.ui.message.screens.embedded.MessageEmbeddedContract
import dk.eboks.app.presentation.ui.message.screens.embedded.MessageEmbeddedPresenter
import dk.eboks.app.presentation.ui.message.screens.opening.MessageOpeningContract
import dk.eboks.app.presentation.ui.message.screens.opening.MessageOpeningPresenter
import dk.eboks.app.presentation.ui.message.screens.reply.ReplyFormContract
import dk.eboks.app.presentation.ui.message.screens.reply.ReplyFormPresenter
import dk.eboks.app.presentation.ui.message.screens.sign.SignContract
import dk.eboks.app.presentation.ui.message.screens.sign.SignPresenter
import dk.eboks.app.presentation.ui.navigation.components.NavBarComponentContract
import dk.eboks.app.presentation.ui.navigation.components.NavBarComponentPresenter
import dk.eboks.app.presentation.ui.overlay.screens.OverlayContract
import dk.eboks.app.presentation.ui.overlay.screens.OverlayPresenter
import dk.eboks.app.presentation.ui.profile.components.HelpContract
import dk.eboks.app.presentation.ui.profile.components.HelpPresenter
import dk.eboks.app.presentation.ui.profile.components.PrivacyContract
import dk.eboks.app.presentation.ui.profile.components.PrivacyPresenter
import dk.eboks.app.presentation.ui.profile.components.drawer.EmailVerificationComponentContract
import dk.eboks.app.presentation.ui.profile.components.drawer.EmailVerificationComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.drawer.FingerHintComponentContract
import dk.eboks.app.presentation.ui.profile.components.drawer.FingerHintComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.drawer.FingerPrintComponentContract
import dk.eboks.app.presentation.ui.profile.components.drawer.FingerPrintComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.drawer.MergeAccountComponentContract
import dk.eboks.app.presentation.ui.profile.components.drawer.MergeAccountComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.drawer.PhoneVerificationComponentContract
import dk.eboks.app.presentation.ui.profile.components.drawer.PhoneVerificationComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.main.ProfileInfoComponentContract
import dk.eboks.app.presentation.ui.profile.components.main.ProfileInfoComponentPresenter
import dk.eboks.app.presentation.ui.profile.components.myinfo.MyInfoComponentContract
import dk.eboks.app.presentation.ui.profile.components.myinfo.MyInfoComponentPresenter
import dk.eboks.app.presentation.ui.profile.screens.ProfileContract
import dk.eboks.app.presentation.ui.profile.screens.ProfilePresenter
import dk.eboks.app.presentation.ui.profile.screens.myinfo.MyInfoContract
import dk.eboks.app.presentation.ui.profile.screens.myinfo.MyInfoPresenter
import dk.eboks.app.presentation.ui.senders.components.SenderGroupsComponentContract
import dk.eboks.app.presentation.ui.senders.components.SenderGroupsComponentPresenter
import dk.eboks.app.presentation.ui.senders.components.categories.CategoriesComponentContract
import dk.eboks.app.presentation.ui.senders.components.categories.CategoriesComponentPresenter
import dk.eboks.app.presentation.ui.senders.components.list.SenderAllListComponentContract
import dk.eboks.app.presentation.ui.senders.components.list.SenderAllListComponentPresenter
import dk.eboks.app.presentation.ui.senders.components.register.RegisterPresenter
import dk.eboks.app.presentation.ui.senders.components.register.RegistrationContract
import dk.eboks.app.presentation.ui.senders.screens.browse.BrowseCategoryContract
import dk.eboks.app.presentation.ui.senders.screens.browse.BrowseCategoryPresenter
import dk.eboks.app.presentation.ui.senders.screens.detail.SenderDetailContract
import dk.eboks.app.presentation.ui.senders.screens.detail.SenderDetailPresenter
import dk.eboks.app.presentation.ui.senders.screens.list.SenderAllListContract
import dk.eboks.app.presentation.ui.senders.screens.list.SenderAllListPresenter
import dk.eboks.app.presentation.ui.senders.screens.overview.SendersOverviewContract
import dk.eboks.app.presentation.ui.senders.screens.overview.SendersOverviewPresenter
import dk.eboks.app.presentation.ui.senders.screens.registrations.PendingContract
import dk.eboks.app.presentation.ui.senders.screens.registrations.PendingPresenter
import dk.eboks.app.presentation.ui.senders.screens.registrations.RegistrationsContract
import dk.eboks.app.presentation.ui.senders.screens.registrations.RegistrationsPresenter
import dk.eboks.app.presentation.ui.senders.screens.segment.SegmentDetailContract
import dk.eboks.app.presentation.ui.senders.screens.segment.SegmentDetailPresenter
import dk.eboks.app.presentation.ui.start.components.signup.SignupComponentContract
import dk.eboks.app.presentation.ui.start.components.signup.SignupComponentPresenter
import dk.eboks.app.presentation.ui.start.screens.StartContract
import dk.eboks.app.presentation.ui.start.screens.StartPresenter
import dk.eboks.app.presentation.ui.uploads.components.UploadOverviewComponentContract
import dk.eboks.app.presentation.ui.uploads.components.UploadOverviewComponentPresenter
import dk.eboks.app.presentation.ui.uploads.screens.UploadsContract
import dk.eboks.app.presentation.ui.uploads.screens.UploadsPresenter
import dk.eboks.app.presentation.ui.uploads.screens.fileupload.FileUploadContract
import dk.eboks.app.presentation.ui.uploads.screens.fileupload.FileUploadPresenter
import dk.nodes.arch.domain.executor.Executor
import dk.nodes.arch.domain.injection.scopes.ActivityScope
import javax.inject.Named

@Module
class PresentationModule {
    @ActivityScope
    @Provides
    fun providePastaPresenter(presenter: PastaPresenter): PastaContract.Presenter {
        return presenter
    }

    @ActivityScope
    @Provides
    fun provideMailOverviewPresenter(presenter: MailOverviewPresenter): MailOverviewContract.Presenter {
        return presenter
    }

    @ActivityScope
    @Provides
    fun provideMailListPresenter(appState: AppStateManager): MailListContract.Presenter {
        return MailListPresenter(appState)
    }

    @ActivityScope
    @Provides
    fun provideFolderPresenter(appState: AppStateManager): FolderContract.Presenter {
        return FolderPresenter(appState)
    }

    @ActivityScope
    @Provides
    fun provideMessagePresenter(
        presenter: MessagePresenter
    ): MessageContract.Presenter {
        return presenter
    }

    @ActivityScope
    @Provides
    fun provideChannelsPresenter(stateManager: AppStateManager): ChannelOverviewContract.Presenter {
        return ChannelOverviewPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideFileUploadPresenter(stateManager: AppStateManager): FileUploadContract.Presenter {
        return FileUploadPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideMessageSheetPresenter(
        stateManager: AppStateManager,
        deleteMessagesInteractor: DeleteMessagesInteractor,
        updateMessageInteractor: UpdateMessageInteractor
    ): MessageEmbeddedContract.Presenter {
        return MessageEmbeddedPresenter(
            stateManager,
            deleteMessagesInteractor,
            updateMessageInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideMessageOpeningPresenter(
        stateManager: AppStateManager,
        executor: Executor,
        openMessageInteractor: OpenMessageInteractor
    ): MessageOpeningContract.Presenter {
        return MessageOpeningPresenter(stateManager, executor, openMessageInteractor)
    }

    @ActivityScope
    @Provides
    fun provideHeaderComponentPresenter(stateManager: AppStateManager): HeaderComponentContract.Presenter {
        return HeaderComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideNotesComponentPresenter(
        stateManager: AppStateManager,
        updateMessageInteractor: UpdateMessageInteractor
    ): NotesComponentContract.Presenter {
        return NotesComponentPresenter(stateManager, updateMessageInteractor)
    }

    @ActivityScope
    @Provides
    fun provideReplyButtonComponentPresenter(stateManager: AppStateManager): ReplyButtonComponentContract.Presenter {
        return ReplyButtonComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideSignButtonComponentPresenter(stateManager: AppStateManager): SignButtonComponentContract.Presenter {
        return SignButtonComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideSignPresenter(
        stateManager: AppStateManager,
        getSignLinkInteractor: GetSignLinkInteractor
    ): SignContract.Presenter {
        return SignPresenter(stateManager, getSignLinkInteractor)
    }

    @ActivityScope
    @Provides
    fun provideAttachmentsComponentPresenter(
        stateManager: AppStateManager,
        openAttachmentInteractor: OpenAttachmentInteractor,
        saveAttachmentInteractor: SaveAttachmentInteractor
    ): AttachmentsComponentContract.Presenter {
        return AttachmentsComponentPresenter(
            stateManager,
            openAttachmentInteractor,
            saveAttachmentInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideFolderInfoComponentPresenter(stateManager: AppStateManager): FolderInfoComponentContract.Presenter {
        return FolderInfoComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideDocumentComponentPresenter(
        stateManager: AppStateManager,
        saveAttachmentInteractor: SaveAttachmentInteractor
    ): DocumentComponentContract.Presenter {
        return DocumentComponentPresenter(stateManager, saveAttachmentInteractor)
    }

    @ActivityScope
    @Provides
    fun providePdfPreviewComponentPresenter(stateManager: AppStateManager): PdfViewComponentContract.Presenter {
        return PdfViewComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideFoldersComponentPresenter(
        stateManager: AppStateManager,
        getFoldersInteractor: GetFoldersInteractor,
        openFolderInteractor: OpenFolderInteractor
    ): FoldersComponentContract.Presenter {
        return FoldersComponentPresenter(stateManager, getFoldersInteractor, openFolderInteractor)
    }

    @ActivityScope
    @Provides
    fun provideFolderShortcutsComponentPresenter(
        stateManager: AppStateManager,
        getCategoriesInteractor: GetCategoriesInteractor,
        openFolderInteractor: OpenFolderInteractor
    ): FolderShortcutsComponentContract.Presenter {
        return FolderShortcutsComponentPresenter(
            stateManager,
            getCategoriesInteractor,
            openFolderInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideSenderCarouselComponentPresenter(
        stateManager: AppStateManager,
        sendersInteractor: GetSendersInteractor
    ): SenderCarouselComponentContract.Presenter {
        return SenderCarouselComponentPresenter(stateManager, sendersInteractor)
    }

    @ActivityScope
    @Provides
    fun provideMailListComponentPresenter(
        stateManager: AppStateManager,
        getMessagesInteractor: GetMessagesInteractor,
        deleteMessagesInteractor: DeleteMessagesInteractor,
        moveMessagesInteractor: MoveMessagesInteractor,
        updateMessageInteractor: UpdateMessageInteractor

    ): MailListComponentContract.Presenter {
        return MailListComponentPresenter(
            stateManager,
            getMessagesInteractor,
            deleteMessagesInteractor,
            moveMessagesInteractor,
            updateMessageInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideNavBarComponentPresenter(stateManager: AppStateManager): NavBarComponentContract.Presenter {
        return NavBarComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideHtmlViewComponentPresenter(stateManager: AppStateManager): HtmlViewComponentContract.Presenter {
        return HtmlViewComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideImageViewComponentPresenter(stateManager: AppStateManager): ImageViewComponentContract.Presenter {
        return ImageViewComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideTextViewComponentPresenter(stateManager: AppStateManager): TextViewComponentContract.Presenter {
        return TextViewComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideQuarantineComponentPresenter(
        stateManager: AppStateManager,
        executor: Executor
    ): QuarantineComponentContract.Presenter {
        return QuarantineComponentPresenter(stateManager, executor)
    }

    @ActivityScope
    @Provides
    fun provideRecalledMessageComponentPresenter(
        stateManager: AppStateManager,
        executor: Executor
    ): RecalledComponentContract.Presenter {
        return RecalledComponentPresenter(stateManager, executor)
    }

    @ActivityScope
    @Provides
    fun providePromulgationComponentPresenter(
        stateManager: AppStateManager,
        executor: Executor
    ): PromulgationComponentContract.Presenter {
        return PromulgationComponentPresenter(stateManager, executor)
    }

    @ActivityScope
    @Provides
    fun provideProtectedMessageComponentPresenter(
        stateManager: AppStateManager,
        executor: Executor
    ): ProtectedMessageComponentContract.Presenter {
        return ProtectedMessageComponentPresenter(stateManager, executor)
    }

    @ActivityScope
    @Provides
    fun provideOpeningReceiptComponentPresenter(
        stateManager: AppStateManager,
        executor: Executor
    ): OpeningReceiptComponentContract.Presenter {
        return OpeningReceiptComponentPresenter(stateManager, executor)
    }

    @ActivityScope
    @Provides
    fun providePrivateSenderWarningComponentPresenter(
        stateManager: AppStateManager,
        executor: Executor
    ): PrivateSenderWarningComponentContract.Presenter {
        return PrivateSenderWarningComponentPresenter(stateManager, executor)
    }

    @ActivityScope
    @Provides
    fun provideChannelListComponentPresenter(
        stateManager: AppStateManager,
        getChannelsInteractor: GetChannelsInteractor
    ): ChannelOverviewComponentContract.Presenter {
        return ChannelOverviewComponentPresenter(stateManager, getChannelsInteractor)
    }

    @ActivityScope
    @Provides
    fun provideChannelSettingsPopUpComponentPresenter(stateManager: AppStateManager): ChannelRequirementsComponentContract.Presenter {
        return ChannelRequirementsComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideShareComponentPresenter(stateManager: AppStateManager): ShareComponentContract.Presenter {
        return ShareComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideSendersOverviewPresenter(
        presenter: SendersOverviewPresenter
    ): SendersOverviewContract.Presenter {
        return presenter
    }

    @ActivityScope
    @Provides
    fun provideStartPresenter(
        stateManager: AppStateManager,
        bootstrapInteractor: BootstrapInteractor,
        prefManager: PrefManager
    ): StartContract.Presenter {
        return StartPresenter(stateManager, bootstrapInteractor, prefManager)
    }

    @ActivityScope
    @Provides
    fun provideSignupComponentPresenter(
        stateManager: AppStateManager,
        createUserInteractor: CreateUserInteractor,
        verifySignupMailInteractor: CheckSignupMailInteractor,
        loginInteractor: LoginInteractor,
        checkSsnExistsInteractor: CheckSsnExistsInteractor,
        setCurrentUserInteractor: SetCurrentUserInteractor
    ): SignupComponentContract.Presenter {
        return SignupComponentPresenter(
            stateManager,
            createUserInteractor,
            loginInteractor,
            verifySignupMailInteractor,
            checkSsnExistsInteractor,
            setCurrentUserInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideWebLoginPresenter(
        stateManager: AppStateManager,
        transformTokenInteractor: TransformTokenInteractor,
        verifyProfileInteractor: VerifyProfileInteractor,
        mergeAndImpersonateInteractor: MergeAndImpersonateInteractor,
        userSettingsManager: UserSettingsManager
    ): WebLoginContract.Presenter {
        return WebLoginPresenter(
            stateManager,
            transformTokenInteractor,
            verifyProfileInteractor,
            mergeAndImpersonateInteractor,
            userSettingsManager
        )
    }

    @ActivityScope
    @Provides
    fun provideNemIdLoginPresenter(
        stateManager: AppStateManager,
        transformTokenInteractor: TransformTokenInteractor,
        verifyProfileInteractor: VerifyProfileInteractor,
        mergeAndImpersonateInteractor: MergeAndImpersonateInteractor,
        userSettingsManager: UserSettingsManager
    ): WebLoginContract.Presenter {
        return NemIdComponentPresenter(
            stateManager,
            transformTokenInteractor,
            verifyProfileInteractor,
            mergeAndImpersonateInteractor,
            userSettingsManager
        )
    }

    @ActivityScope
    @Provides
    fun provideIdPortenComponentPresenter(
        stateManager: AppStateManager,
        transformTokenInteractor: TransformTokenInteractor,
        verifyProfileInteractor: VerifyProfileInteractor,
        mergeAndImpersonateInteractor: MergeAndImpersonateInteractor,
        userSettingsManager: UserSettingsManager
    ): WebLoginContract.Presenter {
        return IdPortenComponentPresenter(
            stateManager,
            transformTokenInteractor,
            verifyProfileInteractor,
            mergeAndImpersonateInteractor,
            userSettingsManager
        )
    }

    @ActivityScope
    @Provides
    fun provideBankIdSEComponentPresenter(
        stateManager: AppStateManager,
        transformTokenInteractor: TransformTokenInteractor,
        verifyProfileInteractor: VerifyProfileInteractor,
        mergeAndImpersonateInteractor: MergeAndImpersonateInteractor,
        userSettingsManager: UserSettingsManager
    ): WebLoginContract.Presenter {
        return BankIdSEComponentPresenter(
            stateManager,
            transformTokenInteractor,
            verifyProfileInteractor,
            mergeAndImpersonateInteractor,
            userSettingsManager
        )
    }

    @ActivityScope
    @Provides
    fun provideBankIdNOComponentPresenter(
        stateManager: AppStateManager,
        transformTokenInteractor: TransformTokenInteractor,
        verifyProfileInteractor: VerifyProfileInteractor,
        mergeAndImpersonateInteractor: MergeAndImpersonateInteractor,
        userSettingsManager: UserSettingsManager
    ): WebLoginContract.Presenter {
        return BankIdNOComponentPresenter(
            stateManager,
            transformTokenInteractor,
            verifyProfileInteractor,
            mergeAndImpersonateInteractor,
            userSettingsManager
        )
    }

    @ActivityScope
    @Provides
    fun provideVerificationComponentPresenter(stateManager: AppStateManager): VerificationComponentContract.Presenter {
        return VerificationComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideUserCarouselComponentPresenter(
        stateManager: AppStateManager,
        userSettingsManager: UserSettingsManager,
        getUsersInteractor: GetUsersInteractor,
        deleteUserInteractor: DeleteUserInteractor
    ): UserCarouselComponentContract.Presenter {
        return UserCarouselComponentPresenter(
            stateManager,
            userSettingsManager,
            getUsersInteractor,
            deleteUserInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideLoginComponentPresenter(
        stateManager: AppStateManager,
        userSettingsManager: UserSettingsManager,
        decryptUserLoginInfoInteractor: DecryptUserLoginInfoInteractor,
        loginInteractor: LoginInteractor,
        checkRSAKeyPresenceInteractor: CheckRSAKeyPresenceInteractor
    ): LoginComponentContract.Presenter {
        return LoginComponentPresenter(
            stateManager,
            userSettingsManager,
            decryptUserLoginInfoInteractor,
            loginInteractor,
            checkRSAKeyPresenceInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideForgotPasswordComponentPresenter(resetPasswordInteractor: ResetPasswordInteractor): ForgotPasswordComponentContract.Presenter {
        return ForgotPasswordComponentPresenter(resetPasswordInteractor)
    }

    @ActivityScope
    @Provides
    fun provideActivationCodeComponentPresenter(
        stateManager: AppStateManager,
        loginInteractor: LoginInteractor
    ): ActivationCodeComponentContract.Presenter {
        return ActivationCodeComponentPresenter(stateManager, loginInteractor)
    }

    @ActivityScope
    @Provides
    fun provideChannelOpeningComponentPresenter(
        stateManager: AppStateManager,
        getChannelInteractor: GetChannelInteractor,
        createStoreboxInteractor: CreateStoreboxInteractor,
        installChannelInteractor: InstallChannelInteractor
    ): ChannelOpeningComponentContract.Presenter {
        return ChannelOpeningComponentPresenter(
            stateManager,
            getChannelInteractor,
            createStoreboxInteractor,
            installChannelInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideChannelVerificationComponentPresenter(stateManager: AppStateManager): ChannelVerificationComponentContract.Presenter {
        return ChannelVerificationComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideChannelContentComponentPresenter(
        stateManager: AppStateManager,
        getChannelContentLinkInteractor: GetChannelContentLinkInteractor
    ): ChannelContentComponentContract.Presenter {
        return ChannelContentComponentPresenter(stateManager, getChannelContentLinkInteractor)
    }

    @ActivityScope
    @Provides
    fun provideChannelContentStoreboxComponentPresenter(
        stateManager: AppStateManager,
        getStoreboxReceiptsInteractor: GetStoreboxReceiptsInteractor,
        getStoreboxCreditCardsInteractor: GetStoreboxCreditCardsInteractor
    ): ChannelContentStoreboxComponentContract.Presenter {
        return ChannelContentStoreboxComponentPresenter(
            stateManager,
            getStoreboxReceiptsInteractor,
            getStoreboxCreditCardsInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideChannelContentStoreboxDetailComponentPresenter(
        stateManager: AppStateManager,
        getStoreboxReceiptInteractor: GetStoreboxReceiptInteractor,
        deleteStoreboxReceiptInteractor: DeleteStoreboxReceiptInteractor,
        saveReceiptInteractor: SaveReceiptInteractor,
        shareReceiptInteractor: ShareReceiptInteractor
    ): ChannelContentStoreboxDetailComponentContract.Presenter {
        return ChannelContentStoreboxDetailComponentPresenter(
            stateManager,
            getStoreboxReceiptInteractor,
            deleteStoreboxReceiptInteractor,
            saveReceiptInteractor,
            shareReceiptInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideChannelSettingsComponentPresenter(
        stateManager: AppStateManager,
        getStoreboxCreditCardsInteractor: GetStoreboxCreditCardsInteractor,
        deleteStoreboxCreditCardInteractor: DeleteStoreboxCreditCardInteractor,
        getStoreboxProfileInteractor: GetStoreboxProfileInteractor,
        putStoreboxProfileInteractor: PutStoreboxProfileInteractor,
        getStoreboxCardLinkInteractor: GetStoreboxCardLinkInteractor,
        deleteStoreboxAccountLinkInteractor: DeleteStoreboxAccountLinkInteractor,
        updateStoreboxFlagsInteractor: UpdateStoreboxFlagsInteractor,
        getChannelInteractor: GetChannelInteractor,
        uninstallChannelInteractor: UninstallChannelInteractor
    ): ChannelSettingsComponentContract.Presenter {
        return ChannelSettingsComponentPresenter(
            stateManager,
            getStoreboxCreditCardsInteractor,
            deleteStoreboxCreditCardInteractor,
            getStoreboxProfileInteractor,
            putStoreboxProfileInteractor,
            getStoreboxCardLinkInteractor,
            deleteStoreboxAccountLinkInteractor,
            updateStoreboxFlagsInteractor,
            getChannelInteractor,
            uninstallChannelInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideChannelContentPresenter(stateManager: AppStateManager): ChannelContentContract.Presenter {
        return ChannelContentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideCategoriesComponentPresenter(
        stateManager: AppStateManager,
        getSenderCategoriesInteractor: GetSenderCategoriesInteractor
    ): CategoriesComponentContract.Presenter {
        return CategoriesComponentPresenter(getSenderCategoriesInteractor)
    }

    @ActivityScope
    @Provides
    fun provideBrowseCategoryPresenter(
        stateManager: AppStateManager,
        getSendersInteractor: GetSendersInteractor
    ): BrowseCategoryContract.Presenter {
        return BrowseCategoryPresenter(stateManager, getSendersInteractor)
    }

    @ActivityScope
    @Provides
    fun provideRegistrationsPresenter(
        stateManager: AppStateManager,
        registrationsInteractor: GetRegistrationsInteractor
    ): RegistrationsContract.Presenter {
        return RegistrationsPresenter(stateManager, registrationsInteractor)
    }

    @ActivityScope
    @Provides
    fun provideSenderGroupsComponentPresenter(stateManager: AppStateManager): SenderGroupsComponentContract.Presenter {
        return SenderGroupsComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideUploadOverviewPresenter(
        stateManager: AppStateManager,
        getStorageInteractor: GetStorageInteractor,
        getLatestUploadsInteractor: GetLatestUploadsInteractor,
        uploadFileInteractor: UploadFileInteractor
    ): UploadOverviewComponentContract.Presenter {
        return UploadOverviewComponentPresenter(
            stateManager,
            getStorageInteractor,
            getLatestUploadsInteractor,
            uploadFileInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideRegisterPresenter(
        stateManager: AppStateManager,
        registerInteractor: RegisterInteractor,
        unRegisterInteractor: UnRegisterInteractor
    ): RegistrationContract.Presenter {
        return RegisterPresenter(stateManager, registerInteractor, unRegisterInteractor)
    }

    @ActivityScope
    @Provides
    fun providePendingPresenter(
        stateManager: AppStateManager,
        getPendingInteractor: GetPendingInteractor,
        registerInteractor: RegisterInteractor,
        unRegisterInteractor: UnRegisterInteractor
    ): PendingContract.Presenter {
        return PendingPresenter(
            stateManager,
            getPendingInteractor,
            registerInteractor,
            unRegisterInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideSenderDetailPresenter(
        stateManager: AppStateManager,
        getSenderDetailInteractor: GetSenderDetailInteractor,
        registerInteractor: RegisterInteractor,
        unRegisterInteractor: UnRegisterInteractor
    ): SenderDetailContract.Presenter {
        return SenderDetailPresenter(
            stateManager,
            getSenderDetailInteractor,
            registerInteractor,
            unRegisterInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideSegmentDetailPresenter(
        stateManager: AppStateManager,
        getSegmentInteractor: GetSegmentInteractor,
        registerInteractor: RegisterInteractor,
        unRegisterInteractor: UnRegisterInteractor
    ): SegmentDetailContract.Presenter {
        return SegmentDetailPresenter(
            stateManager,
            getSegmentInteractor,
            registerInteractor,
            unRegisterInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideDebugOptionsComponentPresenter(
        stateManager: AppStateManager,
        prefManager: PrefManager
    ): DebugOptionsComponentContract.Presenter {
        return DebugOptionsComponentPresenter(stateManager, prefManager)
    }

    @ActivityScope
    @Provides
    fun provideDebugUsersComponentPresenter(
        stateManager: AppStateManager,
        userSettingsManager: UserSettingsManager,
        testLoginStates: MutableList<LoginState>
    ): DebugUsersComponentContract.Presenter {
        return DebugUsersComponentPresenter(stateManager, userSettingsManager, testLoginStates)
    }

    @ActivityScope
    @Provides
    fun provideProfilePresenter(stateManager: AppStateManager): ProfileContract.Presenter {
        return ProfilePresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideProfileInfoComponentPresenter(
        stateManager: AppStateManager,
        saveUserInteractor: SaveUserInteractor,
        saveUserSettingsInteractor: SaveUserSettingsInteractor,
        getUserProfileInteractor: GetUserProfileInteractor,
        userSettingsManager: UserSettingsManager
    ): ProfileInfoComponentContract.Presenter {
        return ProfileInfoComponentPresenter(
            stateManager,
            saveUserInteractor,
            saveUserSettingsInteractor,
            getUserProfileInteractor,
            userSettingsManager
        )
    }

    @ActivityScope
    @Provides
    fun provideMyInfoComponentPresenter(
        stateManager: AppStateManager,
        saveUserInteractor: SaveUserInteractor,
        updateUserInteractor: UpdateUserInteractor,
        getUserProfileInteractor: GetUserProfileInteractor
    ): MyInfoComponentContract.Presenter {
        return MyInfoComponentPresenter(
            stateManager,
            saveUserInteractor,
            updateUserInteractor,
            getUserProfileInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideDebugUserPresenter(
        stateManager: AppStateManager,
        userSettingsManager: UserSettingsManager,
        createUserInteractor: CreateDebugUserInteractorImpl,
        saveUserInteractor: SaveUserInteractor
    ): DebugUserContract.Presenter {
        return DebugUserPresenter(
            stateManager,
            userSettingsManager,
            createUserInteractor,
            saveUserInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideEmailVerificationComponentPresenter(
        stateManager: AppStateManager,
        verifyEmailInteractor: VerifyEmailInteractor
    ): EmailVerificationComponentContract.Presenter {
        return EmailVerificationComponentPresenter(stateManager, verifyEmailInteractor)
    }

    @ActivityScope
    @Provides
    fun providePhoneVerificationComponentPresenter(
        stateManager: AppStateManager,
        verifyPhoneInteractor: VerifyPhoneInteractor,
        confirmPhoneInteractor: ConfirmPhoneInteractor
    ): PhoneVerificationComponentContract.Presenter {
        return PhoneVerificationComponentPresenter(
            stateManager,
            verifyPhoneInteractor,
            confirmPhoneInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideFingerHintComponentPresenter(
        stateManager: AppStateManager,
        userSettingsManager: UserSettingsManager,
        encryptUserLoginInfoInteractor: EncryptUserLoginInfoInteractor,
        saveUserInteractor: SaveUserInteractor
    ): FingerHintComponentContract.Presenter {
        return FingerHintComponentPresenter(
            stateManager,
            userSettingsManager,
            encryptUserLoginInfoInteractor,
            saveUserInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideFingerPrintComponentPresenter(
        stateManager: AppStateManager,
        userSettingsManager: UserSettingsManager,
        encryptUserLoginInfoInteractor: EncryptUserLoginInfoInteractor,
        saveUserInteractor: SaveUserInteractor,
        testLoginInteractor: TestLoginInteractor
    ): FingerPrintComponentContract.Presenter {
        return FingerPrintComponentPresenter(
            stateManager,
            userSettingsManager,
            encryptUserLoginInfoInteractor,
            saveUserInteractor,
            testLoginInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideMergeAccountComponentPresenter(stateManager: AppStateManager): MergeAccountComponentContract.Presenter {
        return MergeAccountComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideUploadsPresenter(stateManager: AppStateManager): UploadsContract.Presenter {
        return UploadsPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideForgotPasswordDoneComponentPresenter(stateManager: AppStateManager): ForgotPasswordDoneComponentContract.Presenter {
        return ForgotPasswordDoneComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideOverlayPresenter(stateManager: AppStateManager): OverlayContract.Presenter {
        return OverlayPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideConnectStoreboxPresenter(
        stateManager: AppStateManager,
        linkStoreboxInteractor: LinkStoreboxInteractor,
        confirmStoreboxInteractor: ConfirmStoreboxInteractor,
        createStoreboxInteractor: CreateStoreboxInteractor
    ): ConnectStoreboxContract.Presenter {
        return ConnectStoreboxPresenter(
            stateManager,
            linkStoreboxInteractor,
            confirmStoreboxInteractor,
            createStoreboxInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideMyInfoPresenter(stateManager: AppStateManager): MyInfoContract.Presenter {
        return MyInfoPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideReplyFormPresenter(
        appState: AppStateManager,
        getReplyFormInteractor: GetReplyFormInteractor,
        submitReplyFormInteractor: SubmitReplyFormInteractor
    ): ReplyFormContract.Presenter {
        return ReplyFormPresenter(appState, getReplyFormInteractor, submitReplyFormInteractor)
    }

    @ActivityScope
    @Provides
    fun provideSenderAllListPresenter(appState: AppStateManager): SenderAllListContract.Presenter {
        return SenderAllListPresenter(appState)
    }

    @ActivityScope
    @Provides
    fun provideSenderAllListComponentPresenter(
        stateManager: AppStateManager,
        sendersInteractor: GetSendersInteractor
    ): SenderAllListComponentContract.Presenter {
        return SenderAllListComponentPresenter(stateManager, sendersInteractor)
    }

    @ActivityScope
    @Provides
    fun provideNewFolderComponentPresenter(
        stateManager: AppStateManager,
        createFolderInteractor: CreateFolderInteractor,
        deleteFolderInteractor: DeleteFolderInteractor,
        editFolderInteractor: EditFolderInteractor
    ): NewFolderComponentContract.Presenter {
        return NewFolderComponentPresenter(
            stateManager,
            createFolderInteractor,
            deleteFolderInteractor,
            editFolderInteractor
        )
    }

    @ActivityScope
    @Provides
    fun provideFolderSelectUserComponentPresenter(
        presenter: FolderSelectUserComponentPresenter
    ): FolderSelectUserComponentContract.Presenter {
        return presenter
    }

    @ActivityScope
    @Provides
    fun provideEkeyContentPresenter(
        presenter: EkeyContentPresenter
    ): EkeyContentContract.Presenter {
        return presenter
    }

    @ActivityScope
    @Provides
    fun provideEkeyComponentPresenter(stateManager: AppStateManager): EkeyComponentContract.Presenter {
        return EkeyComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideEkeyAddItemComponentPresenter(stateManager: AppStateManager): EkeyAddItemComponentContract.Presenter {
        return EkeyAddItemComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideEkeyDetailComponentPresenter(
        presenter: EkeyDetailComponentPresenter
    ): EkeyDetailComponentContract.Presenter {
        return presenter
    }

    @ActivityScope
    @Provides
    fun provideFolderPreviewComponentPresenter(
        presenter: FolderPreviewComponentPresenter
    ): FolderPreviewComponentContract.Presenter {
        return presenter
    }

    @ActivityScope
    @Provides
    fun provideChannelControlComponentPresenter(
        presenter: ChannelControlComponentPresenter
    ): ChannelControlComponentContract.Presenter {
        return presenter
    }

    @ActivityScope
    @Provides
    fun provideEkeyOpenItemComponentPresenter(presenter: EkeyOpenItemComponentPresenter): EkeyOpenItemComponentContract.Presenter =
        presenter

    @ActivityScope
    @Provides
    fun provideEkeyPinComponentPresenter(stateManager: AppStateManager): EkeyPinComponentContract.Presenter {
        return EkeyPinComponentPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun providePrivacyPresenter(@Named("NAME_BASE_URL") baseUrlString: String): PrivacyContract.Presenter {
        return PrivacyPresenter(baseUrlString)
    }

    @ActivityScope
    @Provides
    fun provideHelpPresenter(@Named("NAME_BASE_URL") baseUrlString: String): HelpContract.Presenter {
        return HelpPresenter(baseUrlString)
    }

    @ActivityScope
    @Provides
    fun providePopupLoginPresenter(stateManager: AppStateManager): PopupLoginContract.Presenter {
        return PopupLoginPresenter(stateManager)
    }

    @ActivityScope
    @Provides
    fun provideDeviceActivationComponentPresenter(
        presenter: DeviceActivationComponentPresenter
    ): DeviceActivationComponentContract.Presenter {
        return presenter
    }
  @ActivityScope
    @Provides
    fun providePaymentPresenter(stateManager: AppStateManager) : PaymentComponentContract.Presenter {
        return PaymentComponentPresenter(stateManager)
    }


    /* Pasta
    @ActivityScope
    @Provides
    fun provideComponentPresenter(stateManager: AppStateManager) : ComponentContract.Presenter {
        return ComponentPresenter(stateManager)
    }
    */
}