package rahul.co.neosoft.compose.domain.usecases.contact

import retrofit2.Response
import rahul.co.neosoft.compose.data.webService.ApiHelper
import rahul.co.neosoft.compose.data.webService.ApiService
import rahul.co.neosoft.compose.domain.models.EmployeeResponse
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper {
    override suspend fun getEmployees(): Response<EmployeeResponse>  = apiService.getEmployees()
}