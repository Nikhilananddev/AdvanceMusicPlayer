package com.android.developer.Nikhilanand.audioplayer.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.developer.Nikhilanand.audioplayer.Interface.RecyclerViewClickInterface;
import com.android.developer.Nikhilanand.audioplayer.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.SongHolder> {
    public RecyclerViewClickInterface recyclerViewClickInterface;
    Context context;
    String[] arrayList;

    public MusicAdapter(Context context, String[] stringArrayList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.arrayList = stringArrayList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }


    @NonNull

    @Override
    public MusicAdapter.SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.music_list, parent, false);
        return new SongHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull MusicAdapter.SongHolder holder, int position) {

        holder.textView.setText(arrayList[position]);

    }

    @Override
    public int getItemCount() {
        return arrayList.length;
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public SongHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
