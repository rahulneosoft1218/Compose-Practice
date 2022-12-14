package rahul.co.neosoft.compose.presentation.contact.open

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.runBlocking
import rahul.co.neosoft.compose.presentation.contact.list.ContactListResponseModel
import java.net.URLEncoder
import rahul.co.neosoft.compose.R

@Composable
fun OpenProfileView(
    navController: NavController,
    context: Context
) {
    val data = remember {
        mutableStateOf(
            navController.previousBackStackEntry?.arguments?.getParcelable<ContactListResponseModel>(
                "userDetails"
            )!!
        )
    }
    Column {


        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            elevation = 5.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ) {
            Row {

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .weight(0.5f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Image(
                        modifier = Modifier
                            .size(300.dp)
                            .clip(shape = CircleShape),
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Your Image"
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))

                            data.component1().name?.let {
                                Text(
                                    text = it,
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            data.component1().contact?.let {
                                Text(
                                    text = it,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = Color.Gray,
                                        letterSpacing = (0.8).sp
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                }


            }
        }
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            elevation = 5.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ) {
            Row {
                Row {
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .weight(0.2f)
                    ) {

                        IconButton(onClick = {
                            runBlocking {
                                val packageManager = context.packageManager
                                val i = Intent(Intent.ACTION_VIEW)
                                try {
                                    val url =
                                        "https://api.whatsapp.com/send?phone=" + data.component1().contact + "&text=" + URLEncoder.encode(
                                            "Hi, this is test message",
                                            "UTF-8"
                                        )
                                    i.setPackage("com.whatsapp")
                                    i.data = Uri.parse(url)
                                        context.startActivity(i)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }) {
                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(shape = CircleShape),
                            painter = painterResource(id = R.drawable.whatsap),
                            contentDescription = "Your Image"
                        )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .weight(0.9f)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(text = "WhatsApp")
                    }
                }
            }

        }
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            elevation = 5.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(corner = CornerSize(16.dp))
        ) {
            Row {
                Row {
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .weight(0.2f)
                    ) {

                        IconButton(onClick = {
                            runBlocking {
                                val uri = Uri.parse("http://instagram.com/_u/rahul_1218")
                                val likeIng = Intent(Intent.ACTION_VIEW, uri)

                                likeIng.setPackage("com.instagram.android")

                                try {
                                    context.startActivity(likeIng)
                                } catch (e: ActivityNotFoundException) {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("http://instagram.com/rahul_1218")
                                        )
                                    )
                                }
                            }
                        }) {
                            Image(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(shape = CircleShape),
                                painter = painterResource(id = R.drawable.insta),
                                contentDescription = "Your Image"
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .weight(0.9f)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(text = "Instagram")
                    }
                }
            }

        }
    }
}




