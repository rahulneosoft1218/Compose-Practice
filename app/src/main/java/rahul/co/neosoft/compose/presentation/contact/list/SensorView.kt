package rahul.co.neosoft.compose.presentation.contact.list

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import rahul.co.neosoft.compose.R


@Composable
fun SensorView(
    navController: NavController,
    context: Context
) {

    Scaffold()
    {
        Column(modifier = Modifier
            .padding(16.dp)

        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                }
            }
        proximitySensor(context)
    }

    }

@Composable
fun proximitySensor(context: Context) {
    val ctx = LocalContext.current
    val sensorManager: SensorManager = ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val proximitySensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    val sensorStatus = remember {
        mutableStateOf("")
    }
    val nameState: State<String>
    val proximitySensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            // method to check accuracy changed in sensor.
        }

        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0f) {
                    sensorStatus.value = "Near"
                } else {
                    sensorStatus.value = "Away"
                }
            }
        }
    }

    sensorManager.registerListener(
        proximitySensorEventListener,
        proximitySensor,
        SensorManager.SENSOR_DELAY_NORMAL
    )
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
                        ,
                    painter = painterResource(id = R.drawable.proximity),
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


                        Spacer(modifier = Modifier.height(2.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Your object is",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Default,
                                fontSize = 40.sp, modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = sensorStatus.value,
                                color = Color.Green,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Default,
                                fontSize = 40.sp, modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                text = "from the sensor",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Default,
                                fontSize = 40.sp, modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                }

            }


        }
    }

}


