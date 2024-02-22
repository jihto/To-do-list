package com.example.final_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.Domain.ListsDomain;
import com.example.final_project.R;

import java.util.ArrayList;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.Viewholder> {
    ArrayList<ListsDomain> items;
    Context context;

    public ListsAdapter(ArrayList<ListsDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ListsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list, parent, false);
        context = parent.getContext();
        return new ListsAdapter.Viewholder(inflator);
    }

    @Override
    public void onBindViewHolder(@NonNull ListsAdapter.Viewholder holder, int position) {
        holder.title.setText(items.get(position).getTitle());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView title, subTitle;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
        }
    }
}