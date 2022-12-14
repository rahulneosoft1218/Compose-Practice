package rahul.co.neosoft.compose.domain.repositories

import rahul.co.neosoft.compose.data.interfaces.ContactDataSource
import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.models.ContactRequestModel
import rahul.co.neosoft.compose.domain.models.ContactResponseModel

class ContactRepositoryImpl constructor(private val contactDataSource: ContactDataSource) :
    ContactRepository {
    override suspend fun getContacts(): List<ContactResponseModel> {
        return contactDataSource.getAll()
    }

    override suspend fun getContact(id: Int): ContactResponseModel? {
        return contactDataSource.getOne(id)
    }

    override suspend fun deleteContact(id: Int) {
        return contactDataSource.delete(id)
    }

    override suspend fun updateContact(id: Int, data: ContactRequestModel) {
        return contactDataSource.update(id, data)
    }

    override suspend fun createContact(data: ContactRequestModel) {
        return contactDataSource.create(data)
    }
}