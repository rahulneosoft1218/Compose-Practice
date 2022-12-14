package rahul.co.neosoft.compose.domain.usecases.contact

import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.interfaces.usecases.UpdateContactUseCase
import rahul.co.neosoft.compose.domain.models.ContactRequestModel

class UpdateContact constructor(private val contactRepository: ContactRepository) :
    UpdateContactUseCase {
    override suspend fun execute(id: Int, data: ContactRequestModel) {
        return contactRepository.updateContact(id, data)
    }
}