package com.example.androidapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class deleteItemsActivity extends AppCompatActivity {

    public static EditText resultdeleteview;
    private FirebaseAuth firebaseAuth;
    Button deletebtn;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_items);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getDisplayName()).child("Items");
        resultdeleteview = findViewById(R.id.barcodedelete);
        deletebtn= findViewById(R.id.deleteItemToTheDatabasebtn);

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletefrmdatabase();
            }
        });
    }
    public void deletefrmdatabase()
    {
        String deleteItemName = resultdeleteview.getText().toString();
        if(!TextUtils.isEmpty(deleteItemName)){
            databaseReference.child(deleteItemName).removeValue();
            Toast.makeText(deleteItemsActivity.this,"Item is Deleted",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(deleteItemsActivity.this,"Please scan Barcode",Toast.LENGTH_SHORT).show();
        }
    }
}