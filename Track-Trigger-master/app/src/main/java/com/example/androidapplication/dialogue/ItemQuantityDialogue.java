package com.example.androidapplication.dialogue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.androidapplication.model.Items;
import com.example.androidapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ItemQuantityDialogue extends AppCompatDialogFragment {

    private TextView itemNameTextView;
    private EditText itemQuantityEditText;
    private Items item;
    private DatabaseReference mdatabaseReference;

    public ItemQuantityDialogue(Items item){
        this.item = item;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_change_quantity, null);
        builder.setView(view).setTitle("Change Quantity").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handleExit();
            }
        }).setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int newQuantity = Integer.parseInt(itemQuantityEditText.getText().toString());
                handleChangeQty(newQuantity);
            }
        });

        itemNameTextView = view.findViewById(R.id.item_name);
        itemQuantityEditText = view.findViewById(R.id.item_quantity);

        itemNameTextView.setText(item.getItemname());
        itemQuantityEditText.setText(String.valueOf(item.getItemqty()));

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser users = firebaseAuth.getCurrentUser();
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(users.getDisplayName()).child("Items").child(item.getItemname());

        return builder.create();
    }

    private void handleChangeQty(int newQuantity) {
        item.setItemqty(newQuantity);
        mdatabaseReference.setValue(item);
        handleExit();
    }

    private void handleExit() {
        dismiss();
    }
}
