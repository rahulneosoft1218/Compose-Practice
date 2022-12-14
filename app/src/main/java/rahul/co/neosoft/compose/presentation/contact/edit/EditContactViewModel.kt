package rahul.co.neosoft.compose.presentation.contact.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import rahul.co.neosoft.compose.domain.interfaces.usecases.DeleteContactUseCase
import rahul.co.neosoft.compose.domain.interfaces.usecases.UpdateContactUseCase
import rahul.co.neosoft.compose.domain.models.ContactRequestModel
import java.lang.Exception

class EditContactViewModel constructor(
    private val deleteContactUseCase: DeleteContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase
) :
    ViewModel() {
    private val _errorMessage = mutableStateOf("")
    private val _isBusy = mutableStateOf(false)

    val errorMessage: String
        get() = _errorMessage.value

    val isBusy: Boolean
        get() = _isBusy.value

    suspend fun deleteContact(id: Int) {
        try {
            _isBusy.value = true
            deleteContactUseCase.execute(id)
        } catch (err: Exception) {
            _errorMessage.value = "Error Deleting Contact"
        } finally {
            _isBusy.value = false
        }
    }

    suspend fun updateContact(id: Int, data: ContactRequestModel) {
        try {
            _isBusy.value = true
            updateContactUseCase.execute(id, data)
        } catch (err: Exception) {
            _errorMessage.value = "Error Updating Contact"
        } finally {
            _isBusy.value = false
        }
    }
}