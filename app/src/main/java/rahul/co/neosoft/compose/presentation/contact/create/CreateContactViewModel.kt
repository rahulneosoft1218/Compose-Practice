package rahul.co.neosoft.compose.presentation.contact.create

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rahul.co.neosoft.compose.domain.interfaces.usecases.CreateContactUseCase
import rahul.co.neosoft.compose.domain.models.ContactRequestModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateContactViewModel @Inject constructor(
    private val createContactUseCase: CreateContactUseCase
) :
    ViewModel() {
    private val _errorMessage = mutableStateOf("")
    private val _name = mutableStateOf("")
    private val _contact = mutableStateOf("")

    val name: String
        get() = _name.value
    val contact: String
        get() = _contact.value


    val errorMessage: String
        get() = _errorMessage.value

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onContactChange(contact: String) {
        _contact.value = contact
    }

    suspend fun createContact() {
        try {
            createContactUseCase.execute(ContactRequestModel(null, _name.value,_contact.value))
        } catch (err: Exception) {
            _errorMessage.value = "Error Creating Contact"
        }
    }
}