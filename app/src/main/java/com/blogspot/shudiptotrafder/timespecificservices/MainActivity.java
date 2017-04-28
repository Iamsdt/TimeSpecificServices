package com.blogspot.shudiptotrafder.timespecificservices;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TimePicker timePicker;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        button = (Button) findViewById(R.id.btn);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 23){
                    setTime(timePicker.getHour(),timePicker.getMinute());
                    Log.e("Time","H: "+timePicker.getHour()+" M: "+timePicker.getMinute());
                } else {
                    setTime(timePicker.getCurrentMinute(), timePicker.getCurrentHour());
                    Log.e("Time","H: "+timePicker.getCurrentHour()+" M: "+timePicker.getCurrentMinute());
                }
            }
        });

        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{
                                Manifest.permission.SET_ALARM},
                        121);
                return ;
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 121:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Sorry this is not working anymore", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Oops! you don't give permission", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }

    }

    void setTime(int hour, int min){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,0);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, MyReceiver.class);
        intent.setAction("com.shudipto.trafder");
        intent.putExtra(Intent.EXTRA_TEXT,"oh! it's received");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,101,intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        manager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);
    }

}
