package rahul.co.neosoft.compose.domain.interfaces.usecases

import rahul.co.neosoft.compose.domain.models.ContactRequestModel

interface UpdateContactUseCase {
    suspend fun execute(id: Int, data: ContactRequestModel)
}