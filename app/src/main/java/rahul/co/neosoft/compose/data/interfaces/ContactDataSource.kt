package rahul.co.neosoft.compose.data.interfaces

import rahul.co.neosoft.compose.domain.models.ContactRequestModel
import rahul.co.neosoft.compose.domain.models.ContactResponseModel

interface ContactDataSource {
    suspend fun getAll(): List<ContactResponseModel>
    suspend fun getOne(id: Int): ContactResponseModel?
    suspend fun delete(id: Int)
    suspend fun update(id: Int, contact: ContactRequestModel)
    suspend fun create(data: ContactRequestModel)
}