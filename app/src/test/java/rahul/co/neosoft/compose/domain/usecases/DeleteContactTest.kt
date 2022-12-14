package rahul.co.neosoft.compose.domain.usecases

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.*
import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.usecases.contact.DeleteContact

class DeleteContactTest {
    @Test
    fun should_return_true() = runBlocking {
        val mockContactRepo = mock<ContactRepository>()
        val useCase = DeleteContact(mockContactRepo)
        val result = useCase.execute(1)
        verify(mockContactRepo, times(1)).deleteContact(any())
    }
}