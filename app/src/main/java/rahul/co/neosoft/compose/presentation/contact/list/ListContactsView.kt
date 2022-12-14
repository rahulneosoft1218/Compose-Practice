package rahul.co.neosoft.compose.presentation.contact.list

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import rahul.co.neosoft.compose.R
import rahul.co.neosoft.compose.presentation.contact.open.PuppyImage


@Composable
fun ContactListView(
    navController: NavController,
    vm: ListContactsViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit, block = {
        vm.getContacts()
    })


    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Color.White,
                title = {
                    Text("")
                }, actions = {
                    Button(modifier = Modifier.padding(5.dp).background(color =colorResource(id = R.color.purple_200)), onClick = {
                        navController.navigate("create")

                    }) {
                        Text("Add")
                    }
                    Button(modifier = Modifier.padding(5.dp).background(color =colorResource(id = R.color.purple_200)),onClick = {
                        navController.navigate("open")

                    }) {
                        Text("Load")
                    }
                    Button(modifier = Modifier.padding(5.dp).background(color =colorResource(id = R.color.purple_200), shape = RectangleShape),onClick = {
                        navController.navigate("employee")

                    }) {
                        Text("Employee")
                    }
                }
            )
        }

    )
    {
        Column(modifier = Modifier
            .padding(16.dp)

        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                repeat(5, 1)
                items(vm.contacts) { item ->
                    val transition = rememberInfiniteTransition()
                    val translateAnim by transition.animateFloat(
                        initialValue = 0f,
                        targetValue = 1000f,
                        animationSpec = infiniteRepeatable(
                            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
                            RepeatMode.Restart
                        )
                    )

                    val brush = Brush.linearGradient(
                        colors = ShimmerColorShades,
                        start = Offset(10f, 10f),
                        end = Offset(translateAnim, translateAnim)
                    )
                    ShimmerAnimation()
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            ,
                        elevation = 5.dp,
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(corner = CornerSize(16.dp))
                    ) {
                        Row (modifier = Modifier.background(brush = brush)){
                            PuppyImage()
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()

                                    .weight(0.5f)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Text(
                                    modifier = Modifier.padding(10.dp),
                                    text = item.name!!,
                                    style = MaterialTheme.typography.h6
                                )
                                Text(text = item.contact!!, style = MaterialTheme.typography.caption)
                            }
                        }}
                    }
                }
            }
        }

    }



@Composable
fun ShimmerAnimation(
) {

    /*
    Create InfiniteTransition
    which holds child animation like [Transition]
    animations start running as soon as they enter
    the composition and do not stop unless they are removed
    */
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        /*
        Specify animation positions,
        initial Values 0F means it starts from 0 position
        */
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

//    ShimmerItem(brush = brush)

}

val ShimmerColorShades = listOf(

    Color.LightGray.copy(0.9f),

    Color.LightGray.copy(0.2f),

    Color.LightGray.copy(0.9f)

)
@Composable
fun ShimmerItem(
    brush: Brush
) {

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(vertical = 8.dp)
                .background(brush = brush)
        )
    }
}




