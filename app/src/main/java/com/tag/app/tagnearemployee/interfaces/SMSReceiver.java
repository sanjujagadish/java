package com.tag.app.tagnearemployee.interfaces;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SMS RECEIVER
 */

public class SMSReceiver extends BroadcastReceiver
{
    private static String senderName = "IM-TAGNEAR";
    private static String yulu = "yulu";
    public static SMSListener mListener;
    private String regex = "[0-9]+";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            //You must check here if the sender is your provider and not another one with same text.

            String messageBody = smsMessage.getMessageBody();

            if (sender.contains(senderName))
            {
                Pattern pattern = Pattern.compile(this.regex);
                Matcher matcher = pattern.matcher(messageBody);
                String otp;
                for(otp = ""; matcher.find(); otp = messageBody.substring(matcher.start(), matcher.end()))
                {

                }
                Log.d("OTP==>", otp);
                if (mListener != null) {
                    mListener.messageReceived(otp);
                } else {
                    Log.d("Listener=>", "null");
                }
            }
        }

    }

    public static void bindListener(SMSListener listener) {
        mListener = listener;
    }

}
