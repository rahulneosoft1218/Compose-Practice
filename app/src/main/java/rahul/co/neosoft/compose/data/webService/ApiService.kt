package rahul.co.neosoft.compose.data.webService

import retrofit2.Response
import retrofit2.http.GET
import rahul.co.neosoft.compose.domain.models.EmployeeResponse

interface ApiService {
    @GET("employees")
    suspend fun getEmployees(): Response<EmployeeResponse>
}