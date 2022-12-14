package rahul.co.neosoft.compose.presentation.contact.list

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import rahul.co.neosoft.compose.R
import androidx.annotation.FloatRange
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.style.TextAlign


@Composable
fun CustomeOverlappingView(context: Context) {

    Scaffold()
    {
        Column(modifier = Modifier
            .padding(16.dp)

        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                }
            }
        setAnimatedButton(context)
    }

}
@Composable
fun setAnimatedButton(context: Context) {
    FabAnywhere(context,FabPosition.Center, onClick = {  }) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}

@Composable
fun getCustomUI(context: Context) {
    OverlappingRow(
        overlapFactor = 0.7f
    ) {
        val images = intArrayOf(
            R.drawable.profile,
            R.drawable.profile,
            R.drawable.profile,
            R.drawable.profile,
            R.drawable.profile,
            R.drawable.profile
        )
        for (i in images.indices) {
            Image(
                painter = painterResource(id = images[i]),
                contentDescription = null,
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .border(width = 1.dp, color = Color.White, shape = CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop

            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(74.dp)
                .fillMaxWidth()
                .height(74.dp)
                .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                .clip(CircleShape)
                .background(Color.White),

            ) {
            Text(
                text = "10+",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,

                )
        }
    }

}

@Composable
fun OverlappingRow(
    modifier: Modifier = Modifier,
    @FloatRange(from = 0.1,to = 1.0) overlapFactor:Float = 0.5f,
    content: @Composable () -> Unit,
){
    val measurePolicy = overlappingRowMeasurePolicy(overlapFactor)
    Layout(measurePolicy = measurePolicy,
        content = content,
        modifier = modifier )
}

fun overlappingRowMeasurePolicy(overlapFactor: Float) = MeasurePolicy { measurables, constraints ->
    val placeables = measurables.map { measurable -> measurable.measure(constraints)}
    val height = placeables.maxOf { it.height }
    val width = (placeables.subList(1,placeables.size).sumOf { it.width  }* overlapFactor + placeables[0].width).toInt()
    layout(width,height) {
        var xPos = 0
        for (placeable in placeables) {
            placeable.placeRelative(xPos, 0, 0f)
            xPos += (placeable.width * overlapFactor).toInt()
        }
    }
}

@Composable
fun FabAnywhere(
    context: Context,
    fabPosition: FabPosition,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Scaffold(
        floatingActionButtonPosition = fabPosition,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                modifier = modifier,
                content = content
            )
        }
    ) {
        getCustomUI(context)
    }
}

