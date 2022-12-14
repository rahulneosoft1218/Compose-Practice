package rahul.co.neosoft.compose.domain.interfaces.usecases

import rahul.co.neosoft.compose.domain.models.ContactRequestModel

interface CreateContactUseCase {
    suspend fun execute(contact: ContactRequestModel)
}