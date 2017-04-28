package com.blogspot.shudiptotrafder.timespecificservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase("com.shudipto.trafder")){

            Bundle bundle = intent.getExtras();

            Toast.makeText(context, "Message: "+bundle.getString(Intent.EXTRA_TEXT), Toast.LENGTH_SHORT).show();

            NewMessageNotification.notify(context,bundle.getString(Intent.EXTRA_TEXT),2);
        }

    }
}
