package rahul.co.neosoft.compose.presentation.contact.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rahul.co.neosoft.compose.domain.models.Employee
import rahul.co.neosoft.compose.domain.repositories.MainRepository
import javax.inject.Inject

@HiltViewModel
class EmployeeViewModel @Inject constructor(
    private val mainRepository: MainRepository
):ViewModel() {
    var list : List<Employee> = emptyList()

    suspend fun getEmployees()  = viewModelScope.launch {
        mainRepository.getEmployee().let {
            if (it.isSuccessful){
                list = it.body()?.data!!
            }
        }
    }
}