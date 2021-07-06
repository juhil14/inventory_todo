package com.example.androidapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidapplication.helper.ProfessionHelperClass;
import com.example.androidapplication.R;
import com.example.androidapplication.helper.UserHelperClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Prof extends AppCompatActivity {

    EditText Profession;
    Button btn;
    FirebaseDatabase rootNode;
    DatabaseReference reference,reference_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);
        Profession=findViewById(R.id.Pro);
        btn=findViewById(R.id.button2);
        GoogleSignInAccount sigInAccount;
        sigInAccount = GoogleSignIn.getLastSignedInAccount(this);


        //add data to firebase database
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode= FirebaseDatabase.getInstance();
                reference=rootNode.getReference("users");
                reference_2=rootNode.getReference("Profession");

                String name=sigInAccount.getDisplayName();
                String password=" ";
                String email=sigInAccount.getEmail();
                UserHelperClass helpers =new UserHelperClass(name,email,password);
                reference.child(name).setValue(helpers);

                String get_profession=Profession.getText().toString().trim();
                ProfessionHelperClass piroo=new ProfessionHelperClass(get_profession);
                reference_2.child(name).setValue(piroo);
                startActivity(new Intent(getApplicationContext(), Otp.class));
                finish();
            }
        });
    }
}