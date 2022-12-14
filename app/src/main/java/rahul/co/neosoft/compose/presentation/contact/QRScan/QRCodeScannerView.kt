package rahul.co.neosoft.compose.presentation.contact.QRScan

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import rahul.co.neosoft.compose.MainActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import android.util.Size
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.core.app.ActivityCompat.finishAffinity


@Composable
fun QRCodeScannerView(context: Context) {

    Scaffold()
    {
        Column(modifier = Modifier
            .padding(16.dp)

        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                }
            }
        startQRScanner(context)
    }

}
@Composable
fun startQRScanner(context: Context) {
    var code by remember {
        mutableStateOf("")
    }
    var hasReadCode by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFeature = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCamPermission = granted
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (hasCamPermission) {
            if (hasReadCode) {
                LoadWebUrl(code,context)
                BackHandler {
                    restartApp(context)
                }
            } else {
                AndroidView(
                    factory = { context ->
                        val previewView = PreviewView(context)
                        val preview = Preview.Builder().build()
                        val selector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()
                        preview.setSurfaceProvider(previewView.surfaceProvider)
                        val imageAnalysis = ImageAnalysis.Builder()
                            .setTargetResolution(
                                Size(
                                    previewView.width,
                                    previewView.height
                                )
                            )
                            .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                            .build()
                        imageAnalysis.setAnalyzer(
                            ContextCompat.getMainExecutor(context),
                            QRCode { result ->
                                code = result
                                hasReadCode = true
                            }
                        )
                        try {
                            cameraProviderFeature.get().bindToLifecycle(
                                lifecycleOwner,
                                selector,
                                preview,
                                imageAnalysis
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        previewView
                    },
                    modifier = Modifier.border(10.dp, color = Color(0xFFD6A2E8))
                )
                /*Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "",
                    modifier = Modifier.size(270.dp)
                )*/
            }
        }
    }
}


@Composable
fun LoadWebUrl(url: String,context: Context) {
    Text(text = "This QR code having : "+url, color = Color.Black, fontSize = 40.sp)
    AndroidView(factory = {
        WebView(context).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}

private fun restartApp(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    context.startActivity(intent)
    finishAffinity(context as Activity)
}






