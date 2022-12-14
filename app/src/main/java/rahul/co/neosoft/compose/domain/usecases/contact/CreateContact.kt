package rahul.co.neosoft.compose.domain.usecases.contact

import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.interfaces.usecases.CreateContactUseCase
import rahul.co.neosoft.compose.domain.models.ContactRequestModel

class CreateContact constructor(private val contactRepository: ContactRepository) :
    CreateContactUseCase {
    override suspend fun execute(contact: ContactRequestModel) {
        return contactRepository.createContact(contact)
    }
}