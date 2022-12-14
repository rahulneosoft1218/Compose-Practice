package rahul.co.neosoft.compose.data.datasources.room

import rahul.co.neosoft.compose.data.datasources.room.entities.*
import rahul.co.neosoft.compose.data.interfaces.ContactDao
import rahul.co.neosoft.compose.data.interfaces.ContactDataSource
import rahul.co.neosoft.compose.domain.models.ContactRequestModel
import rahul.co.neosoft.compose.domain.models.ContactResponseModel


class RoomContactDataSource constructor(private val dao: ContactDao) : ContactDataSource {
    override suspend fun getAll(): List<ContactResponseModel> {
        return dao.getAll().toList().map { it.toContactResponseModel() };
    }

    override suspend fun getOne(id: Int): ContactResponseModel? {
        val entity = dao.getById(id)
        if (entity != null) {
            return entity.toContactResponseModel()
        }
        return null
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun update(id: Int, contact: ContactRequestModel) {
        dao.update(id, contact.name)
    }

    override suspend fun create(data: ContactRequestModel) {
        dao.insert(data.toContactRoomEntity())
    }
}