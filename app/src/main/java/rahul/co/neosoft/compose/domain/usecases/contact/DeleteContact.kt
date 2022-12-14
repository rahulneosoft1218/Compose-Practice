package rahul.co.neosoft.compose.domain.usecases.contact

import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.interfaces.usecases.DeleteContactUseCase

class DeleteContact constructor(private val contactRepository: ContactRepository) :
    DeleteContactUseCase {
    override suspend fun execute(id: Int) {
        return contactRepository.deleteContact(id)
    }
}