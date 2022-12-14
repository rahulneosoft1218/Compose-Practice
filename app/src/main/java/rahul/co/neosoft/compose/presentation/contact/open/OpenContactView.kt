package rahul.co.neosoft.compose.presentation.contact.open

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.runBlocking
import rahul.co.neosoft.compose.R
import rahul.co.neosoft.compose.presentation.contact.list.ContactListResponseModel
import java.util.*
import kotlin.collections.ArrayList


@Composable
fun OpenContactView(
    navController: NavController,
    vm: OpenContactViewModel = hiltViewModel(),
    context: Context
) {

    LaunchedEffect(Unit, block = {
        vm.loadContacts()
    })
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Load Contact")
                }
            )
        }

    ) {
        Column(modifier = Modifier
            .padding(10.dp)
        ) {
            val textState = remember { mutableStateOf(TextFieldValue("")) }
            SearchView(textState)
            ItemList(state = textState,vm.contacts)

            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .shadow(0.dp, shape = RoundedCornerShape(15.dp))
            ) {
                items(vm.contacts) { item ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        elevation = 5.dp,
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(corner = CornerSize(16.dp))
                    ) {
                        Row {
                            PuppyImage()
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                                    .weight(0.5f)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Text(modifier = Modifier.padding(10.dp), text = item.contact!!, style = typography.h6)
                                Text(text = item.name!!, style = typography.caption)
                            }
                            Column( modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .weight(0.5f)
                                .align(Alignment.CenterVertically)) {
                                Button(modifier = Modifier
                                    .padding(3.dp)
                                    .fillMaxWidth(1f),onClick = {
                                    runBlocking {
                                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + item.name))
                                        context.startActivity(intent)
                                    }
                                }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_call_24),
                                        modifier = Modifier.size(18.dp),
                                        contentDescription = "drawable icons"
                                    )
                                    Text(text = "Call")
                                }
                                Button(modifier = Modifier
                                    .padding(3.dp)
                                    .fillMaxWidth(1f), onClick = {
                                    runBlocking {
                                        val userDetails = ContactListResponseModel(
                                            id = "1",
                                            name = item.contact,
                                            contact = item.name
                                        )
                                        navController.currentBackStackEntry?.arguments?.apply {
                                            putParcelable("userDetails",userDetails)
                                        }
                                        navController.navigate("profile")
                                    }
                                }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_profile),
                                        modifier = Modifier
                                            .size(18.dp)
                                            .align(Alignment.CenterVertically),
                                        contentDescription = "drawable icons"
                                    )
                                    Text(text = "Profile",textAlign = TextAlign.End)
                                }

                            }
                        }
                    }
                }
            }

        }
    }

}
@Composable
fun PuppyImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_profile),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.tint(Color.Black),
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}




@Composable
fun ItemList(state: MutableState<TextFieldValue>, contacts: List<ContactListResponseModel>) {
//    val items by remember { mutableStateOf(listOf("Drink water", "Walk")) }
    val items = contacts

    var filteredItems: List<ContactListResponseModel>
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        val searchedText = state.value.text
        filteredItems = if (searchedText.isEmpty()) {
            items
        } else {
            val resultList = ArrayList<ContactListResponseModel>()
            for (item in items) {
                if (item.contact?.lowercase(Locale.getDefault())
                        ?.contains(searchedText.lowercase(Locale.getDefault()))!!
                ) {
                    resultList.add(item)
                }
            }
            resultList
        }
        items(filteredItems) { filteredItem ->
            filteredItem.contact?.let {
                ItemListItem(
                    ItemText = it,
                    onItemClick = { state.value =TextFieldValue("000")
                    }
                )
            }
        }

    }
}



@Composable
fun ItemListItem(ItemText: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(ItemText) })
            .background(colorResource(id = R.color.purple_700))
            .height(57.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Text(text = ItemText, fontSize = 18.sp, color = Color.White)
    }
}


