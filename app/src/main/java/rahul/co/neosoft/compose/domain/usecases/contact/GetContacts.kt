package rahul.co.neosoft.compose.domain.usecases.contact

import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.interfaces.usecases.GetAllContactsUseCase
import rahul.co.neosoft.compose.domain.models.ContactResponseModel

class GetContacts constructor(private val contactRepository: ContactRepository) :
    GetAllContactsUseCase {
    override suspend fun execute(): List<ContactResponseModel> {
        return contactRepository.getContacts()
    }
}