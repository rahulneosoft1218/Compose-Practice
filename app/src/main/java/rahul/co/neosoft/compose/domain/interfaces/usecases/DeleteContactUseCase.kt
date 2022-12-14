package rahul.co.neosoft.compose.domain.interfaces.usecases

interface DeleteContactUseCase {
    suspend fun execute(id: Int)
}