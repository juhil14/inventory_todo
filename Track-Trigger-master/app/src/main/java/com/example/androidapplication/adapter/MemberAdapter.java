package com.example.androidapplication.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapplication.activities.EditEvent;
import com.example.androidapplication.R;
import com.example.androidapplication.model.Member;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    Context context;
    List<Member> members;
    public MemberAdapter(Context c, List<Member> p)
    {
        context=c;
        members=p;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.listings_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.taskText.setText(members.get(position).getTask());
        holder.timeAndDateText.setText(members.get(position).getTime()+" "+members.get(position).getDate());
        final String task2=members.get(position).getTask();
        final String time2=members.get(position).getTime();
        final String date2=members.get(position).getDate();
        final String key2=members.get(position).getKey();
        final String key22=members.get(position).getKey2();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a1=new Intent(context, EditEvent.class);
                a1.putExtra("task",task2);
                a1.putExtra("time",time2);
                a1.putExtra("date",date2);
                a1.putExtra("key",key2);
                a1.putExtra("key2",key22);
                context.startActivity(a1);

            }
        });
    }


    @Override
    public int getItemCount() {
        return members.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView taskText, timeAndDateText;
        private LinearLayout topLayout1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskText=(TextView) itemView.findViewById(R.id.event);
            timeAndDateText=(TextView) itemView.findViewById(R.id.timeAndDate);


        }
    }
}