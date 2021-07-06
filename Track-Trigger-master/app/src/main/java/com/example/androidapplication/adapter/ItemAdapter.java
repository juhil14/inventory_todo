package com.example.androidapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.dialogue.ItemQuantityDialogue;
import com.example.androidapplication.model.Items;
import com.example.androidapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ItemAdapter extends FirebaseRecyclerAdapter<Items,ItemAdapter.ItemHolder> {
    private Context context;

    public ItemAdapter(@NonNull FirebaseRecyclerOptions<Items> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull Items model) {
        holder.viewitemname.setText(model.getItemname());
        holder.viewItemQty.setText(String.valueOf(model.getItemqty()));
        holder.viewitemcategory.setText(model.getItemcategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemQuantityDialogue itemQuantityDialogue = new ItemQuantityDialogue(model);
                itemQuantityDialogue.show(((AppCompatActivity)context).getSupportFragmentManager(),"Change Quantity");
            }
        });

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);

        return new ItemHolder(view);
    }

    class ItemHolder extends RecyclerView.ViewHolder{
        TextView viewitemname,viewItemQty,viewitemcategory;
        View parentView;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            parentView = itemView;
            viewitemname=itemView.findViewById(R.id.viewitemname);
            viewItemQty=itemView.findViewById(R.id.viewitemqty_new);
//            viewItemQty=itemView.findViewById(R.id.viewitemqtyname);
            viewitemcategory=itemView.findViewById(R.id.viewitemcategory);

        }
    }

}
