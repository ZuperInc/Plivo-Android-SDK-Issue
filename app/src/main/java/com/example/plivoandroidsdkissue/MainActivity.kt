package com.example.plivoandroidsdkissue

import android.media.AudioDeviceInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.plivoandroidsdkissue.ui.theme.PlivoAndroidSdkIssueTheme
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.plivo.endpoint.Endpoint
import com.plivo.endpoint.EventListener
import com.plivo.endpoint.Incoming
import com.plivo.endpoint.Outgoing

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlivoAndroidSdkIssueTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MapView(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}

@OptIn(MapboxExperimental::class)
@Composable
fun MapView(modifier: Modifier = Modifier) {
    var endpoint by remember {
        mutableStateOf<Endpoint?>(null)
    }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        endpoint = Endpoint.newInstance(
            context,
            true,
            object : EventListener {
                override fun onLogin() {
                    Log.d(MainActivity.TAG, "Plivo Login Successful")
                }

                override fun onLogout() {
                    Log.d(MainActivity.TAG, "Plivo Logout Successful")
                }

                override fun onLoginFailed() {
                    Log.e(MainActivity.TAG, "Plivo Login Failed")
                }

                override fun onLoginFailed(p0: String?) {
                    Log.e(MainActivity.TAG, "Plivo Login Failed $p0")
                }

                override fun onIncomingCall(p0: Incoming?) {
                    Log.d(MainActivity.TAG, "Plivo On Incoming Call ${p0?.callId}")
                }

                override fun onIncomingCallConnected(p0: Incoming?) {
                    Log.d(MainActivity.TAG, "Plivo On Incoming Call Connected ${p0?.callId}")
                }

                override fun onIncomingCallHangup(p0: Incoming?) {
                    Log.d(MainActivity.TAG, "Plivo On Incoming Call Hangup ${p0?.callId}")
                }

                override fun onIncomingCallRejected(p0: Incoming?) {
                    Log.d(MainActivity.TAG, "Plivo On Incoming Call Rejected ${p0?.callId}")
                }

                override fun onIncomingCallInvalid(p0: Incoming?) {
                    Log.e(MainActivity.TAG, "Plivo On Incoming Call Invalid ${p0?.callId}")
                }

                override fun onOutgoingCall(p0: Outgoing?) {
                    Log.d(MainActivity.TAG, "Plivo On Outgoing Call ${p0?.callId}")
                }

                override fun onOutgoingCallRinging(p0: Outgoing?) {
                    Log.d(MainActivity.TAG, "Plivo On Outgoing Call Ringing ${p0?.callId}")
                }

                override fun onOutgoingCallAnswered(p0: Outgoing?) {
                    Log.d(MainActivity.TAG, "Plivo On Outgoing Call Answered ${p0?.callId}")
                }

                override fun onOutgoingCallRejected(p0: Outgoing?) {
                    Log.d(MainActivity.TAG, "Plivo On Outgoing Call Rejected ${p0?.callId}")
                }

                override fun onOutgoingCallHangup(p0: Outgoing?) {
                    Log.d(MainActivity.TAG, "Plivo On Outgoing Call Hangup ${p0?.callId}")
                }

                override fun onOutgoingCallInvalid(p0: Outgoing?) {
                    Log.e(MainActivity.TAG, "Plivo On Outgoing Call Invalid ${p0?.callId}")
                }

                override fun mediaMetrics(p0: HashMap<String, Any>?) {
                    Log.d(MainActivity.TAG, "Plivo Media Metrics ${p0.toString()}")
                }

                override fun onPermissionDenied(p0: String?) {
                    Log.e(MainActivity.TAG, "Plivo On Permission Denied $p0")
                }

                override fun audioDeviceChange(p0: String?, p1: AudioDeviceInfo?) {
                    Log.d(MainActivity.TAG, "Plivo On Audio Device Change $p0")
                }

                override fun speakingOnMute() {
                    Log.d(MainActivity.TAG, "Plivo Speaking On Mute")
                }
            },
        )
    }
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        MapboxMap(
            modifier = Modifier.fillMaxSize()
        )
        Button(
            onClick = {
                // TODO - Please change username and password here
                endpoint?.login(
                    "CHANGE_USERNAME",
                    "CHANGE_PASSWORD"
                )
            },
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(text = "Plivo login")
        }
    }
}