package rahul.co.neosoft.compose.domain.interfaces.usecases
import rahul.co.neosoft.compose.domain.models.ContactResponseModel

interface GetAllContactsUseCase {
    suspend fun execute(): List<ContactResponseModel>
}