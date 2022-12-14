package rahul.co.neosoft.compose.presentation.contact.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.coroutines.runBlocking
import rahul.co.neosoft.compose.presentation.components.TextInput


@Composable
fun CreateContactView(
    navController: NavController,
    vm: CreateContactViewModel = hiltViewModel()
) {


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("New Contact")
                }, actions = {
                    Button(onClick = {
                        runBlocking {
                            vm.createContact()
                            navController.popBackStack()
                        }

                    }) {
                        Text("Save")
                    }
                }
            )
        }

    ) {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            // passing a file name to share a preferences
            "preferences",
            masterKeyAlias,
            LocalContext.current,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val name = sharedPreferences.getString("name", "")
        val contact = sharedPreferences.getString("contact", "")
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)

        ) {
            TextInput(name?:vm.name, { value -> vm.onNameChange(value) }, "Name")
            TextInput(contact?:vm.contact, { value -> vm.onContactChange(value) }, "Contact")
            Button(onClick = {
                runBlocking {
                    sharedPreferences.edit().putString("name", vm.name).apply()
                    sharedPreferences.edit().putString("contact", vm.contact).apply()
                }

            }) {
                Text("Add")
            }
        }
    }

}


