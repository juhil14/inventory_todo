package com.example.androidapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.R;
import com.example.androidapplication.model.Items;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Map;

public class ItemShareAdapter extends FirebaseRecyclerAdapter<Items, ItemShareAdapter.ItemShareHolder> {
    private Context context;
    private Map<Items, Boolean> checkedState;

    public ItemShareAdapter(@NonNull FirebaseRecyclerOptions<Items> options, Map<Items, Boolean> map, Context context) {
        super(options);
        this.context = context;
        this.checkedState = map;
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemShareHolder holder, int position, @NonNull Items model) {

        boolean state = checkedState.getOrDefault(model, false);
        holder.itemSelectCheckBox.setChecked(state);
        holder.itemSelectCheckBox.setText(model.getItemname());

        holder.itemSelectCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedState.put(model, isChecked);
            }
        });

    }

    @NonNull
    @Override
    public ItemShareHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout_share, parent, false);

        return new ItemShareHolder(view);
    }

    class ItemShareHolder extends RecyclerView.ViewHolder{
        CheckBox itemSelectCheckBox;
        public ItemShareHolder(@NonNull View itemView) {
            super(itemView);
            itemSelectCheckBox = itemView.findViewById(R.id.share_check_box);

        }
    }

}
