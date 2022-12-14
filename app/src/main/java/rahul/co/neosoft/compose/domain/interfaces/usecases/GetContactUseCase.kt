package rahul.co.neosoft.compose.domain.interfaces.usecases

import rahul.co.neosoft.compose.domain.models.ContactResponseModel

interface GetContactUseCase {
    suspend fun execute(id: Int): ContactResponseModel?
}