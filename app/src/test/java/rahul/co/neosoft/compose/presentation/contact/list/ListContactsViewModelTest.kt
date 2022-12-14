package rahul.co.neosoft.compose.presentation.contact.list

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import rahul.co.neosoft.compose.domain.interfaces.usecases.GetAllContactsUseCase
import rahul.co.neosoft.compose.domain.models.ContactResponseModel

class ListContactsViewModelTest {
    @Test
    fun should_set_contacts_with_data() = runBlocking {
        val mockUseCase = mock<GetAllContactsUseCase>()
        val expectedResult = listOf(
            ContactResponseModel(id = 1, name = "Paul",
                number = "7483920987"),
            ContactResponseModel(id = 2, name = "John",
                number = "7483920987")
        )
        whenever(mockUseCase.execute()).thenReturn(expectedResult)
        val vm = ListContactsViewModel(mockUseCase)
        vm.getContacts()

        assertEquals(vm.errorMessage, "")
    }

    @Test
    fun should_set_error_when_getContacts_fails() = runBlocking {
        val mockUseCase = mock<GetAllContactsUseCase>()
        whenever(mockUseCase.execute()).thenThrow()
        val vm = ListContactsViewModel(mockUseCase)
        vm.getContacts()
        assertEquals(vm.contacts.count(), 0)
        assertEquals(vm.errorMessage, "Error Fetching Contacts")

    }
}