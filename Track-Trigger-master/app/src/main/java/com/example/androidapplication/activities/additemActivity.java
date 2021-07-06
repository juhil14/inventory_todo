package com.example.androidapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapplication.model.Items;
import com.example.androidapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class additemActivity extends AppCompatActivity {
    private EditText itemname,itemcategory,itemQty;
    private FirebaseAuth firebaseAuth;
    public static TextView resulttextview;
    Button scanbutton, additemtodatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencecat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = databaseReference.child(user.getDisplayName());

        additemtodatabase = findViewById(R.id.additembuttontodatabase);
        itemname = findViewById(R.id.edititemname);
        itemcategory= findViewById(R.id.editcategory);
        itemQty = findViewById(R.id.editqty);


        // String result = finaluser.substring(0, finaluser.indexOf("@"));

        additemtodatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                additem();
            }
        });


    }

    // addding item to databse
    public  void additem() {

        try {
            String itemnameValue = itemname.getText().toString();
            String itemcategoryValue = itemcategory.getText().toString();
            int itemQuantity = Integer.valueOf(itemQty.getText().toString());


            if (!TextUtils.isEmpty(itemnameValue) && !TextUtils.isEmpty(itemcategoryValue) && itemQuantity > 0) {

                Items items = new Items(itemnameValue, itemcategoryValue, itemQuantity);
                databaseReference.child("Items").child(itemnameValue).setValue(items);
                itemname.setText("");
                itemQty.setText("");
                itemcategory.setText("");
                Toast.makeText(additemActivity.this, itemnameValue + " Added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(additemActivity.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception exp){
            Toast.makeText(additemActivity.this, "Please Fill all the fields properly", Toast.LENGTH_SHORT).show();
        }
    }
}