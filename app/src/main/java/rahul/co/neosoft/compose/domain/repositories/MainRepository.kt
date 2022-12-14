package rahul.co.neosoft.compose.domain.repositories

import rahul.co.neosoft.compose.data.webService.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getEmployee() = apiHelper.getEmployees()
}