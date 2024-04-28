package com.learn.permissionsexamplehandler

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.reflect.KFunction1

@Composable
fun PermissionDialog(
    perimission: PermissionTextProvider,
    isPermmanentlyDeclined: Boolean,
    onDismiss: ()-> Unit,
    onOkClick: ()->Unit,
    onGoTopAppSettinsClick: ()->Unit,
    modifier: Modifier = Modifier ){
    AlertDialog(
        title = {
            Text(text = "Permission Required ")
        },
        text = {
            Text(text = perimission.getDescirption(isPermmanentlyDeclined))
        },
        onDismissRequest =    onDismiss,

        confirmButton = {
            Column(modifier = Modifier.fillMaxWidth() , horizontalAlignment = Alignment.CenterHorizontally) {
                Divider(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth())
                TextButton(
                    onClick = {
                        if(isPermmanentlyDeclined)
                            onGoTopAppSettinsClick()
                        else
                            onOkClick()
                    }
                ) {
                    Text(
                        text = if(isPermmanentlyDeclined) {"Grant permission"}else {"Ok"},
                        textAlign = TextAlign.Center
                    )
                }
            }

        },


        modifier = modifier
    )


    }

interface PermissionTextProvider {
        fun  getDescirption(isPermmanentlyDeclined: Boolean)  : String
}
class CameraPermissionTextProvider : PermissionTextProvider {
    override fun getDescirption(isPermmanentlyDeclined: Boolean): String {
        return if(isPermmanentlyDeclined) {
            "it seems you permanently declined  camera permission ." +
                    "you can go to the app settings to grant it "
        }else{
            "This app needs access to your camera so that your friends can see you in a call "

        }
    }
}


class AudioRecordPermissionTextProvider : PermissionTextProvider {
    override fun getDescirption(isPermmanentlyDeclined: Boolean): String {
        return if(isPermmanentlyDeclined) {
            "it seems you permanently declined Audio Record  permission ." +
                    "you can go to the app settings to grant it "
        }else{
            "This app needs access  to your Audio Record so that your friends can hear  you "

        }
    }
}


class PhoneCallPermissionTextProvider : PermissionTextProvider {
    override fun getDescirption(isPermmanentlyDeclined: Boolean): String {
        return if(isPermmanentlyDeclined) {
            "it seems you permanently declined Phone Call  permission ." +
                    "you can go to the app settings to grant it "
        }else{
            "This app needs access to your Audio Phone Call so that you can  call your friends from this  app"

        }
    }
}