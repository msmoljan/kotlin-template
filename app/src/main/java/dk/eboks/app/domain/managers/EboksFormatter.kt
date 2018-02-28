package dk.eboks.app.domain.managers

import dk.eboks.app.domain.models.Content
import dk.eboks.app.domain.models.Message

/**
 * Created by bison on 19/02/18.
 */
interface EboksFormatter
{
    fun formatDate(target : Message) : String
    fun formatDateRelative(target : Message) : String
    fun formatSize(target : Content) : String
}