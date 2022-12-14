package rahul.co.neosoft.compose.domain.models

data class EmployeeResponse(
    val data: List<Employee>,
    val status: String?=""
)
