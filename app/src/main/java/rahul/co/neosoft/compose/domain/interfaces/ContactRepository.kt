package rahul.co.neosoft.compose.domain.interfaces

import rahul.co.neosoft.compose.domain.models.ContactRequestModel
import rahul.co.neosoft.compose.domain.models.ContactResponseModel

interface ContactRepository {
    suspend fun getContacts(): List<ContactResponseModel>
    suspend fun getContact(id: Int): ContactResponseModel?
    suspend fun deleteContact(id: Int)
    suspend fun updateContact(id: Int, data: ContactRequestModel)
    suspend fun createContact(data: ContactRequestModel)
}