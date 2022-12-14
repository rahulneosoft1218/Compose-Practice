package rahul.co.neosoft.compose.domain.models

data class ContactResponseModel(
    val id: Int,
    val name: String,
    val number: String,
)


data class ContactRequestModel(
    val id: Int? = null,
    val name: String,
    val number: String
)
