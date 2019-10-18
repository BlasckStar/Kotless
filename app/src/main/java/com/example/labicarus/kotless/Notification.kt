package com.example.labicarus.kotless

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.view.View

class Notification{

    companion object{
        var notificationManager: NotificationManager? = null
    }


    fun configureNotification(context: Context){
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationChannel(
            "com.example.labicarus.kotless",
            "NOTIFICATION TESTE",
            "SAMPLE TESTE"
        )
    }

    fun sendNotification(view: View, title: String, description: String, context: Context){
        val notificationID = 101
        val channelID = "com.example.labicarus.kotless"
        val notification = Notification.Builder(context, channelID)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setChannelId(channelID)
            .build()

        notificationManager?.notify(notificationID, notification)
    }

    private fun createNotificationChannel(id: String, name: String, description: String){
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(id, name, importance)

        channel.description = description
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        notificationManager?.createNotificationChannel(channel)
    }
}