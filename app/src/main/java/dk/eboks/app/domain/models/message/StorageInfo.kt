package dk.eboks.app.domain.models.message

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by bison on 24-06-2017.
 */

@Parcelize
data class StorageInfo(
    var total: Int,
    var used: Int
) : Parcelable