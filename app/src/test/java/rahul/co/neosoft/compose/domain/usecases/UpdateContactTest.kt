package rahul.co.neosoft.compose.domain.usecases

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.*
import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.models.ContactRequestModel
import rahul.co.neosoft.compose.domain.usecases.contact.UpdateContact

class UpdateContactTest {
    @Test
    fun should_return_true() = runBlocking {
        val mockContactRepo = mock<ContactRepository>()
        val useCase = UpdateContact(mockContactRepo)
        val result = useCase.execute(1, ContactRequestModel(name = "Paul",
            number = "7483920987"))
        verify(mockContactRepo, times(1)).updateContact(any(), any())
    }
}