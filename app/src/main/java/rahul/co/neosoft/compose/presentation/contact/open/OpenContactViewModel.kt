package rahul.co.neosoft.compose.presentation.contact.open

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rahul.co.neosoft.compose.domain.interfaces.usecases.CreateContactUseCase
import rahul.co.neosoft.compose.presentation.contact.list.ContactListResponseModel
import javax.inject.Inject

@HiltViewModel
class OpenContactViewModel @Inject constructor(
    private val createContactUseCase: CreateContactUseCase, application: Application
) :
    AndroidViewModel(application) {
    var app:Application= application

    private val _errorMessage = mutableStateOf("")
    private val _contacts = mutableStateListOf<ContactListResponseModel>()

    val errorMessage: String
        get() = _errorMessage.value


    val contacts: List<ContactListResponseModel>
        get() = _contacts.toList()

    @SuppressLint("Range")
    suspend fun loadContacts(){
        val PEOPLE_PROJECTION = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.Contacts._ID
        )
        val selection = ContactsContract.Contacts.HAS_PHONE_NUMBER
         val resolver: ContentResolver = app.contentResolver;
            val cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PEOPLE_PROJECTION, selection, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " DESC");

        if (cursor != null) {
            if (cursor.count >= 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                    val column =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val phoneNumber = cursor.getString(column)
                    val column_name =
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val name = cursor.getString(column_name)
                    val model:ContactListResponseModel = ContactListResponseModel("",phoneNumber,name)
                    val ids =  hashSetOf<Int>()
                    if (!ids.contains(id)) {
                        ids.add(id)
                        _contacts.add(model)
                    }
                }

            }
        }
        if (cursor != null) {
            cursor.close()
        }
        }
    }


