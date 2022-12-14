package rahul.co.neosoft.compose.presentation.contact.employee

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import rahul.co.neosoft.compose.R
import rahul.co.neosoft.compose.domain.models.Employee
import rahul.co.neosoft.compose.presentation.contact.open.PuppyImage


@Composable
fun EmployeeView(
    navController: NavController,
    vm: EmployeeViewModel = hiltViewModel(),
    context: Context
) {
    var list : List<Employee> = emptyList()
    LaunchedEffect(Unit, block = {
        vm.getEmployees()
        delay(2000)
    })
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Employee")
                }
            )
        }

    ) {
        Column(modifier = Modifier
            .padding(10.dp)
        ) {
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .shadow(0.dp, shape = RoundedCornerShape(15.dp))
            ) {
                items(vm.list) { item ->
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
                                Text(modifier = Modifier.padding(10.dp), text = item.employee_name!!, style = typography.h6)
                                Text(text = item.employee_salary!!, style = typography.caption)
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
                                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + item.id))
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
                                /*Button(modifier = Modifier
                                    .padding(3.dp)
                                    .fillMaxWidth(1f), onClick = {
                                    runBlocking {
                                        val userDetails = ContactListResponseModel(
                                            id = "1",
                                            name = item.employee_name,
                                            contact = item.employee_age
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
                                }*/

                            }
                        }
                    }
                }
            }

        }
    }

}


