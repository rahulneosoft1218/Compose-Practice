package rahul.co.neosoft.compose.domain.usecases.contact

import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.interfaces.usecases.GetContactUseCase
import rahul.co.neosoft.compose.domain.models.ContactResponseModel

class GetOneContact constructor(private val contactRepository: ContactRepository) :
    GetContactUseCase {
    override suspend fun execute(id: Int): ContactResponseModel? {
        return contactRepository.getContact(id)
    }
}