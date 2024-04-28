package com.learn.permissionsexamplehandler

import android.os.Bundle
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learn.permissionsexamplehandler.ui.theme.PermissionsExampleHandlerTheme

class MainActivity : ComponentActivity() {

    private fun openAppSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null))
            .also { intent ->
                startActivity(intent)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PermissionsExampleHandlerTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val ApplicationPermissions = arrayOf (
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE ,
                        Manifest.permission.RECORD_AUDIO )

                    val viewModel = viewModel<PermissionsViewModel>()
                    val dialogQueue = viewModel.visiblePermissionDialogQueue

                    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                        permissions.keys.forEach{ perm ->
                            viewModel.onPermissionResult(
                                permission = perm ,
                                isGranted = permissions[perm] == true  )}
                    }


                    dialogQueue
                        .reversed()
                        .forEach{permission ->
                            PermissionDialog(
                                perimission = when (permission){
                                    Manifest.permission.CAMERA -> {
                                        CameraPermissionTextProvider()
                                    }
                                    Manifest.permission.RECORD_AUDIO -> {
                                        AudioRecordPermissionTextProvider()
                                    }
                                    Manifest.permission.CALL_PHONE -> {
                                        PhoneCallPermissionTextProvider()
                                    }
                                    else -> return@forEach
                                } ,
                                isPermmanentlyDeclined = !shouldShowRequestPermissionRationale(LocalContext.current as Activity , permission),
                                onDismiss = viewModel::dismissDialog ,
                                onOkClick = { viewModel.dismissDialog()
                                    multiplePermissionResultLauncher.launch(
                                        ApplicationPermissions
                                    )
                                },
                                onGoTopAppSettinsClick =   ::openAppSettings
                            )
                        }

                    Column (modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center ,
                        horizontalAlignment = Alignment.CenterHorizontally){
                        Button(onClick = {
                            openAppSettings()
                        }) {
                            Text(text = "request one permission ")

                        }
                        Button(onClick = {
                            multiplePermissionResultLauncher.launch(
                                ApplicationPermissions
                            )
                        }) {
                            Text(text = "request multiple permissions ")
                        }
                    }
                }

                }
            }
        }
    }




@Composable
fun Homepage() {
    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally){
        Button(onClick = {
         }) {
            Text(text = "request one permission ")
        }
        Button(onClick = {

        }) {
            Text(text = "request mutilple permissions ")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PermissionsExampleHandlerTheme {
        Homepage( )
    }
}