package rahul.co.neosoft.compose.data.datasources.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import rahul.co.neosoft.compose.domain.models.ContactRequestModel
import rahul.co.neosoft.compose.domain.models.ContactResponseModel

@Entity(tableName = "tb_contact")
data class ContactRoomEntity(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val contact: String,
)


fun ContactRoomEntity.toContactResponseModel(): ContactResponseModel {
    return ContactResponseModel(
        id = id!!,
        name = name,
        number = contact,
    )
}


fun ContactRequestModel.toContactRoomEntity(): ContactRoomEntity {
    return ContactRoomEntity(
        id = id,
        name = name,
        contact = number
    )
}