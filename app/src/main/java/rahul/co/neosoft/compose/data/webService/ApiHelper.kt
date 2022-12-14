package rahul.co.neosoft.compose.data.webService

import retrofit2.Response
import rahul.co.neosoft.compose.domain.models.EmployeeResponse

interface ApiHelper {
    suspend fun getEmployees(): Response<EmployeeResponse>
}