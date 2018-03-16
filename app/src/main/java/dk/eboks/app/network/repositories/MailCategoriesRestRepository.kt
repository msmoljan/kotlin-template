package dk.eboks.app.network.repositories

import dk.eboks.app.domain.models.folder.Folder
import dk.eboks.app.domain.repositories.MailCategoriesRepository
import dk.eboks.app.domain.exceptions.RepositoryException
import dk.eboks.app.injection.modules.MailCategoryStore
import dk.eboks.app.network.base.SynchronizedBaseRepository
import java.io.IOException
import java.net.UnknownHostException

/**
 * Created by bison on 01/02/18.
 */
class MailCategoriesRestRepository(val mailCategoryStore: MailCategoryStore) : MailCategoriesRepository, SynchronizedBaseRepository() {

    override fun getMailCategories(cached: Boolean): List<Folder> {
        try {
            lock()
            val result = if(cached) mailCategoryStore.get(0).blockingGet() else mailCategoryStore.fetch(0).blockingGet()
            unlock()
            if(result == null) {
                throw(RepositoryException(-1, "darn"))
            }
            return result
        }
        catch (e : Throwable)
        {
            e.printStackTrace()
            if(e.cause != null) {
                when(e.cause) {
                    is UnknownHostException -> throw(RepositoryException(-1, "UnknownHostException"))
                    is IOException -> throw(RepositoryException(-1, "IOException"))
                    else -> throw(RepositoryException(-1, "UnknownException"))
                }
            }
            else
                throw(RepositoryException(-1, "Unknown"))
        }
        finally {
            unlock()
        }
    }
}