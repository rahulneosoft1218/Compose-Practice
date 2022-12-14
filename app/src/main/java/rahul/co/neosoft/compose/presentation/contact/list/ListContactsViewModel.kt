package rahul.co.neosoft.compose.presentation.contact.list

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rahul.co.neosoft.compose.domain.interfaces.usecases.GetAllContactsUseCase
import rahul.co.neosoft.compose.domain.models.ContactResponseModel
import javax.inject.Inject

data class ContactListResponseModel(
    val id: String?,
    val name: String?,
    val contact: String?,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0!!.writeStringArray(
            arrayOf(
                id,
                name,
                contact
            )
        )
    }

    companion object CREATOR : Parcelable.Creator<ContactListResponseModel> {
        override fun createFromParcel(parcel: Parcel): ContactListResponseModel {
            return ContactListResponseModel(parcel)
        }

        override fun newArray(size: Int): Array<ContactListResponseModel?> {
            return arrayOfNulls(size)
        }
    }
}

fun ContactResponseModel.toContactListResponseModel(): ContactListResponseModel {
    return ContactListResponseModel(
        id = id.toString(),
        name = name,
        contact = number,
    )
}


@HiltViewModel
class ListContactsViewModel @Inject constructor(
    private val getAllContactsUseCase: GetAllContactsUseCase
) :
    ViewModel() {
    private val _errorMessage = mutableStateOf("")
    private val _contacts = mutableStateListOf<ContactListResponseModel>()

    val errorMessage: String
        get() = _errorMessage.value


    val contacts: List<ContactListResponseModel>
        get() = _contacts.toList()

    suspend fun getContacts() {
        try {
            _contacts.clear()
            val list = getAllContactsUseCase.execute()
            _contacts.addAll(list.map { it.toContactListResponseModel() })
        } catch (err: Exception) {
            _errorMessage.value = "Error Fetching Contacts"
        }
    }
}