package rahul.co.neosoft.compose.domain.usecases

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.*
import rahul.co.neosoft.compose.domain.interfaces.ContactRepository
import rahul.co.neosoft.compose.domain.models.ContactResponseModel
import rahul.co.neosoft.compose.domain.usecases.contact.GetOneContact

class GetOneContactTest {
    @Test
    fun should_return_data() = runBlocking {
        val mockContactRepo = mock<ContactRepository>()
        val expectedResponse = ContactResponseModel(id = 1, name = "Paul",
            number = "7483920987")
        whenever(mockContactRepo.getContact(any())).thenReturn(expectedResponse)
        val useCase = GetOneContact(mockContactRepo)
        val result = useCase.execute(1)
        verify(mockContactRepo, times(1)).getContact(any())
        Assert.assertEquals(result, expectedResponse)
    }
}