package com.supremehyo.mvvmProject.Util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log

class SMSReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("ㅋㅋ", "BroadcastReceiver Received");

        if (intent != null) {
            if ("android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
                //Bundle bundle = intent.getExtras();
                var bundle = intent.extras
                val messages =
                    bundle!!["pdus"] as Array<Any>?
                val smsMessage =
                    arrayOfNulls<SmsMessage>(messages!!.size)

                for(i in 0 .. smsMessage.size) {
                    smsMessage.set(i, SmsMessage.createFromPdu(messages.get(i) as ByteArray?))
                }

                var message = smsMessage[0]?.getMessageBody().toString();
                Log.d("ee", "SMS Message: " + message);
            }
        }
    }
}

