package com.example.androidapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.R;
import com.example.androidapplication.adapter.ItemAdapter;
import com.example.androidapplication.adapter.ItemShareAdapter;
import com.example.androidapplication.model.Items;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private HashMap<Items, Boolean> stateMap = new HashMap<>();
    private ItemShareAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_inventory);
        mRecyclerView = findViewById(R.id.shareItemRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        Button shareButton = findViewById(R.id.shareButton);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleShareButtonClick();
            }
        });

        final FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("users").child(users.getDisplayName()).child("Items"), Items.class)
                        .build();


        adapter=new ItemShareAdapter(options, stateMap, this);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void handleShareButtonClick() {
        List<Items> itemsList = new ArrayList<>();
        for(Map.Entry<Items, Boolean> entry:stateMap.entrySet()){
            if(entry.getValue() == true){
                itemsList.add(entry.getKey());
            }
        }

        if(itemsList.size() > 0) {


            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, convertToText(itemsList));
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }

    }

    private String convertToText(List<Items> itemsList) {
        StringBuilder builder = new StringBuilder();
        for(Items items:itemsList){
            builder.append(convertToText(items));
            builder.append("\n");
            builder.append("---------------------");
            builder.append("\n");
        }

        return builder.toString();
    }

    private String convertToText(Items items){
        StringBuilder builder = new StringBuilder();
        builder.append("Name :: ").append(items.getItemname());
        builder.append("\n");
        builder.append("Category :: ").append(items.getItemcategory());
        builder.append("\n");
        builder.append("Item Quantity :: ").append(items.getItemqty());
        builder.append("\n");
        return builder.toString();
    }
}
