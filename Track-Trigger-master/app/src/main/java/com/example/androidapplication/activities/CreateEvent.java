package com.example.androidapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.androidapplication.R;
import com.example.androidapplication.model.Member;
import com.example.androidapplication.receiver.AlarmReceiver;
import com.example.androidapplication.receiver.AlarmReceiver1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Random;

public class CreateEvent extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    Button btn_time, btn_date, btn_done;
    EditText editText_task;
    String timeToNotify;
    DatabaseReference reference;
    Member member;
    int h, mi, d, mo, y;
    String event1, date1, time1;
    int key, key3;
    String key1, key2;
    String currentuser;
    FirebaseUser currentuser1;
    FirebaseAuth fAuth;
    String timer;
    Spinner spinner;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        btn_date = findViewById(R.id.btn_date);
        btn_time = findViewById(R.id.btn_time);
        btn_done = findViewById(R.id.btn_done);
        editText_task = (EditText) findViewById(R.id.editText_task);
        btn_date.setOnClickListener(this);
        btn_time.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        key = new Random().nextInt();
        key1 = Integer.toString(key);
        spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.times, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        reference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_date) {
            selectDate();
        } else if (v == btn_time) {
            selectTime();
        } else if (v == btn_done) {
            submit();
        }
    }

    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int Minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeToNotify = hourOfDay + ":" + minute;
                btn_time.setText(FormatTime(hourOfDay, minute));
                h = hourOfDay;
                mi = minute;
            }
        }, hour, Minute, false);
        timePickerDialog.show();
    }

    private void selectDate() {
        Calendar calendar1 = Calendar.getInstance();
        int Year = calendar1.get(Calendar.YEAR);
        int Month = calendar1.get(Calendar.MONTH);
        int day = calendar1.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btn_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                y = year;
                mo = month;
                d = dayOfMonth;
            }
        }, Year, Month, day);
        datePickerDialog.show();
    }

    private void submit() {
        event1 = (editText_task.getText().toString());
        date1 = (btn_time.getText().toString());
        time1 = (btn_date.getText().toString());
        if ((editText_task.getText().toString().trim()).isEmpty()) {
            Toast.makeText(this, "Please enter Task", Toast.LENGTH_SHORT).show();
        } else if (((btn_time.getText().toString()).equals("Select Time")))
            Toast.makeText(this, "Please enter Time", Toast.LENGTH_SHORT).show();
        else if (((btn_date.getText().toString()).equals("Select Date")))
            Toast.makeText(this, "Please enter Date", Toast.LENGTH_SHORT).show();
        else {
            key2 = (Long.toString(System.currentTimeMillis()));
            member = new Member();
            member.setDate(btn_date.getText().toString().trim());
            member.setTime(btn_time.getText().toString().trim());
            member.setTask(editText_task.getText().toString().trim());
            member.setKey(key1);
            member.setKey2(key2);
            currentuser = fAuth.getInstance().getCurrentUser().getUid();
            currentuser1= fAuth.getInstance().getCurrentUser();
            email=currentuser1.getEmail();
            reference.child("To-Do").child(currentuser).child("Task" + key2).setValue(member);
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show();
            setAlarm();
            finish();
        }

    }

    public String FormatTime(int hour1, int minute1) {
        String time = "";
        String formattedMinute;
        if (minute1 / 10 == 0)
            formattedMinute = "0" + minute1;
        else
            formattedMinute = "" + minute1;
        if (hour1 == 0)
            time = "12" + ":" + formattedMinute + "AM";
        if (hour1 < 10)
            time = "0" + hour1 + ":" + formattedMinute + "AM";
        if ((hour1 < 12) && (hour1 >= 10))
            time = hour1 + ":" + formattedMinute + "AM";
        if (hour1 == 12)
            time = "12" + ":" + formattedMinute + "PM";
        if ((hour1 > 12) && (hour1 < 22))
            time = "0" + (hour1 - 12) + ":" + formattedMinute + "PM";
        if (hour1 >= 22)
            time = hour1 + ":" + formattedMinute + "PM";
        return (time);
    }

    private void setAlarm() {
        int x=10;
        int nh=0,nd=0;
        if(timer=="At the time of task")
        {
            nd=d;
            nh=h;
        }
        if(timer=="One Hour Before")
            x=1;
        if(timer=="Two Hours Before")
            x=2;
        if(timer=="Three Hours Before")
            x=3;
        if(timer=="Six Hours Before")
            x=6;
        if(timer=="Twelve Hours Before")
            x=12;
        if(timer=="One Day Before") {
            x=0;
            nd = d - 1;
            nh = h;
        }
        if((x!=0)&&(h>=x))
        {
            nd=d;
            nh=h-x;
        }
        if((x!=0)&&(h<x))
        {
            nd=d-1;
            nh=24-x+h;
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        Intent smsIntent=new Intent(this, AlarmReceiver1.class);
        notificationIntent.putExtra("event", event1);
        notificationIntent.putExtra("time", time1);
        notificationIntent.putExtra("date", date1);
        smsIntent.putExtra("event",event1);
        smsIntent.putExtra("time",time1);
        smsIntent.putExtra("date",date1);
        smsIntent.putExtra("email",email);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, key, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent broadcast1 = PendingIntent.getBroadcast(this,(key+1),smsIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.YEAR, y);
        cal.set(Calendar.MONTH, mo);
        cal.set(Calendar.DAY_OF_MONTH, nd);
        cal.set(Calendar.HOUR_OF_DAY, nh);
        cal.set(Calendar.MINUTE, mi);
        cal.set(Calendar.SECOND, 0);


        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast1);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        timer=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        timer="At the time of task";
    }
}