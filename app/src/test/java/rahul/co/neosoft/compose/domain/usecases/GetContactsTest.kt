package rahul.co.neosoft.compose.domain.usecases

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.*
import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.models.ContactResponseModel
import rahul.co.neosoft.compose.domain.usecases.contact.GetContacts

class GetContactsTest {
    @Test
    fun should_return_data() = runBlocking {
        val mockContactRepo = mock<ContactRepository>()
        val expected = listOf(
            ContactResponseModel(
                id = 1,
                name = "Paul",
                number = "7483920987"
            )
        )
        whenever(mockContactRepo.getContacts()).thenReturn(expected)
        val useCase = GetContacts(mockContactRepo)
        val result = useCase.execute()
        verify(mockContactRepo, times(1)).getContacts()
        Assert.assertEquals(result, expected)
    }
}