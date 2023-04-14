package com.example.vkr.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushService : FirebaseMessagingService() {

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("Ntf", "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d("Ntf", "Message data payload: ${remoteMessage.data}")

        }

        remoteMessage.notification?.let {
            Log.d("Ntf", "Message Notification Body: ${it.body}")
        }
    }
}