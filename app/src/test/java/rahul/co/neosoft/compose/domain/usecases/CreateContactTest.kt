package rahul.co.neosoft.compose.domain.usecases

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.*
import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.models.ContactRequestModel
import rahul.co.neosoft.compose.domain.usecases.contact.CreateContact

class CreateContactTest {
    @Test
    fun should_return_true() = runBlocking {
        val mockContactRepo = mock<ContactRepository>()
        val useCase = CreateContact(mockContactRepo)
        val result = useCase.execute(ContactRequestModel(name = "Paul", number = "9898787656"))
        verify(mockContactRepo, times(1)).createContact(any())
    }
}