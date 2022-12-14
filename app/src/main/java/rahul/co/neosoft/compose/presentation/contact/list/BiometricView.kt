package rahul.co.neosoft.compose.presentation.contact.list

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import rahul.co.neosoft.compose.R
import java.util.concurrent.Executor
import android.hardware.biometrics.BiometricPrompt
import android.os.CancellationSignal


@Composable
fun BiometricView(
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
        getPrompt(context)
    }

    }
private lateinit var executor: Executor
private var cancellationSignal: CancellationSignal? = null
var sensorStatus = mutableStateOf("")

@Composable
fun getPrompt(context: Context) {
    val ctx = LocalContext.current
    sensorStatus = remember {
        mutableStateOf("")
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

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .weight(0.5f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally

            ) {
                /*Button(modifier = Modifier
                    .padding(3.dp)
                    .fillMaxWidth(1f),onClick = {
                    runBlocking {
                        launchBiometric(context)
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_call_24),
                        modifier = Modifier.size(18.dp),
                        contentDescription = "drawable icons"
                    )
                    Text(text = "Call")
                }*/

                Image(
                    modifier = Modifier
                        .size(300.dp)
                        ,
                    painter = painterResource(id = R.drawable.ic_baseline_fingerprint),
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
                                text = sensorStatus.value,
                                color = Color.Red,
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
    launchBiometric(context)

}

@RequiresApi(Build.VERSION_CODES.Q)
private fun launchBiometric(context: Context) {
    val mainExecutor = ContextCompat.getMainExecutor(context)
    if (checkBiometricSupport(context)) {
        var authenticationCallback: BiometricPrompt.AuthenticationCallback =
        @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                sensorStatus.value = "हां , चलो ठीक है. देख लो जो देखना था.."
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                sensorStatus.value = "झूठे, चल निकल यहाँ से.."
            }

            override fun onAuthenticationFailed() {
                sensorStatus.value = "झूठे, चल निकल यहाँ से.."
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                super.onAuthenticationHelp(helpCode, helpString)
            }
        }

        val biometricPrompt = BiometricPrompt.Builder(context)
            .apply {
                setTitle("कौन है भाई तू ?")
                setSubtitle("मोबाइल तेरा है ?")
                setDescription("अच्छा ? तो अंगूठा लगा के दिखा  ")
                setConfirmationRequired(false)
                setNegativeButton("Cancel", mainExecutor, { a, b->
//                    sensorStatus.value = "यही करना था तो क्यों आया इधर "
                    sensorStatus.value = "झूठे, चल निकल यहाँ से.."
                })
            }.build()

        biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
    }
}


private fun getCancellationSignal(): CancellationSignal {
    cancellationSignal = CancellationSignal()
    cancellationSignal?.setOnCancelListener {
//        Toast.makeText(this, "Authentication Cancelled Signal", Toast.LENGTH_SHORT).show()
    }

    return cancellationSignal as CancellationSignal
}

private fun checkBiometricSupport(context: Context): Boolean {
    val keyGuardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

    if (!keyGuardManager.isDeviceSecure) {
        return true
    }
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
        return false
    }

    return context.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
}


