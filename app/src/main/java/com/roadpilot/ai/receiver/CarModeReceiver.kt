package com.roadpilot.ai.receiver

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.os.Build
import com.roadpilot.ai.service.LocationService
import com.roadpilot.ai.service.VoiceAssistantService

class CarModeReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_DOCK_EVENT -> {
                val dockState = intent.getIntExtra(Intent.EXTRA_DOCK_STATE, -1)
                
                when (dockState) {
                    Intent.EXTRA_DOCK_STATE_CAR,
                    Intent.EXTRA_DOCK_STATE_DESK,
                    Intent.EXTRA_DOCK_STATE_LE_DESK,
                    Intent.EXTRA_DOCK_STATE_HE_DESK -> {
                        // Car mode activated
                        activateCarMode(context)
                    }
                    -1, // Undocked
                    Intent.EXTRA_DOCK_STATE_UNDOCKED -> {
                        // Car mode deactivated
                        deactivateCarMode(context)
                    }
                }
            }
            
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                @Suppress("DEPRECATION")
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                
                device?.let {
                    // Check if it's a car stereo or handsfree device
                    val deviceName = it.name ?: ""
                    if (deviceName.contains("car", ignoreCase = true) ||
                        deviceName.contains("auto", ignoreCase = true) ||
                        deviceName.contains("vehicle", ignoreCase = true) ||
                        it.bluetoothClass?.majorDeviceClass == 0x0700) { // PHONE
                        activateCarMode(context)
                    }
                }
            }
            
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                // Don't deactivate immediately, give some time
                // In a real app, you'd track this more carefully
            }
            
            "android.hardware.usb.action.USB_DEVICE_ATTACHED" -> {
                // USB device attached - activate car mode
                activateCarMode(context)
            }
        }
    }
    
    private fun activateCarMode(context: Context) {
        // Start location service
        val locationIntent = Intent(context, LocationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(locationIntent)
        } else {
            context.startService(locationIntent)
        }
        
        // Start voice assistant
        val voiceIntent = Intent(context, VoiceAssistantService::class.java).apply {
            action = VoiceAssistantService.ACTION_START_LISTENING
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(voiceIntent)
        } else {
            context.startService(voiceIntent)
        }
        
        // Broadcast car mode activated
        context.sendBroadcast(Intent(ACTION_CAR_MODE_ACTIVATED))
    }
    
    private fun deactivateCarMode(context: Context) {
        // Broadcast car mode deactivated
        context.sendBroadcast(Intent(ACTION_CAR_MODE_DEACTIVATED))
    }
    
    companion object {
        const val ACTION_CAR_MODE_ACTIVATED = "com.roadpilot.ai.CAR_MODE_ACTIVATED"
        const val ACTION_CAR_MODE_DEACTIVATED = "com.roadpilot.ai.CAR_MODE_DEACTIVATED"
    }
}
