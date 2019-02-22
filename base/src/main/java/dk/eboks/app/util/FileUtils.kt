package dk.eboks.app.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import dk.eboks.app.base.BuildConfig
import dk.eboks.app.domain.models.Translation
import timber.log.Timber
import java.io.File

/**
 * Created by bison on 21/02/18.
 */
object FileUtils {
    fun openExternalViewer(cur_context: Context, filename: String, mimeType: String): Boolean {
        // handler.post {
        Timber.e("Opening document $filename $mimeType in external viewer")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        val uri = FileProvider.getUriForFile(
            cur_context,
            BuildConfig.APPLICATION_ID + ".fileprovider",
            File(filename)
        )
        intent.setDataAndType(uri, mimeType)
        Timber.e("URI $uri")
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (intent.resolveActivity(cur_context.packageManager) != null) {
            cur_context.startActivity(
                Intent.createChooser(
                    intent,
                    Translation.error.attachmentOpenWith
                )
            )
            return true
        }
        Timber.e("Could not resolve share intent - failed to open documents")
        return false
    }
}