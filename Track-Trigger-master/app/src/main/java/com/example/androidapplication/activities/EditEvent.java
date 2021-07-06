package com.example.androidapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapplication.R;
import com.example.androidapplication.model.Member;
import com.example.androidapplication.receiver.AlarmReceiver;
import com.example.androidapplication.receiver.AlarmReceiver1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditEvent extends AppCompatActivity implements View.OnClickListener {
    Button btn_time1, btn_date1, btn_delete1;
    TextView Text_task1;
    DatabaseReference reference;
    Member member;
    String event1, date1, time1;
    int key4;
    String a, b;
    String key3,key5;
    String currentuser;
    String email;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        btn_date1 = findViewById(R.id.btn_date1);
        btn_time1 = findViewById(R.id.btn_time1);
        btn_delete1 = findViewById(R.id.btn_delete1);
        Text_task1 = (TextView) findViewById(R.id.Text_task1);
        btn_date1.setText(getIntent().getStringExtra("date"));
        btn_time1.setText(getIntent().getStringExtra("time"));
        Text_task1.setText(getIntent().getStringExtra("task"));
        key3=getIntent().getStringExtra("key");
        key4=Integer.parseInt(key3);
        key5=getIntent().getStringExtra("key2");
        btn_delete1.setOnClickListener(this);
        reference = FirebaseDatabase.getInstance().getReference();
        a = getIntent().getStringExtra("date");
        b = getIntent().getStringExtra("time");
    }

    @Override
    public void onClick(View v) {
        if (v == btn_delete1) {
            delete();
        }
    }

    private void delete() {
        cancelAlarm();
        currentuser = fAuth.getInstance().getCurrentUser().getUid();
        reference.child("To-Do").child(currentuser).child("Task"+key5).removeValue();

        finish();
    }


    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra("event", event1);
        notificationIntent.putExtra("time", time1);
        notificationIntent.putExtra("date", date1);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, key4, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(broadcast);
        Intent smsIntent = new Intent(this, AlarmReceiver1.class);
        smsIntent.putExtra("event", event1);
        smsIntent.putExtra("time", time1);
        smsIntent.putExtra("date", date1);
        smsIntent.putExtra("email",email);
        PendingIntent broadcast1 = PendingIntent.getBroadcast(this, (key4+1), smsIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(broadcast1);
    }
}