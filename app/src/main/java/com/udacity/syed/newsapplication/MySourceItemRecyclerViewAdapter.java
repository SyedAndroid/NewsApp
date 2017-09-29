package com.udacity.syed.newsapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by syed on 2017-06-27.
 */


public class MySourceItemRecyclerViewAdapter extends RecyclerView.Adapter<MySourceItemRecyclerViewAdapter.SourceViewHolder> {
    OnItemCheckListener onItemClick;
    boolean[] ischecked;
    List<Source> sources;

    public MySourceItemRecyclerViewAdapter(List<Source> sources, @NonNull OnItemCheckListener onItemCheckListener) {
        this.sources = sources;
        this.onItemClick = onItemCheckListener;
        ischecked = new boolean[sources.size()];
        for (int i = 0; i < sources.size(); i++)
            ischecked[i] = false;

    }

    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForListItem = R.layout.fragment_sourceitem;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutForListItem, parent, false);
        return new SourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SourceViewHolder holder, final int position) {

        final Source source = sources.get(position);
        String name = source.getName();
        holder.textView.setVisibility(View.GONE);
        holder.checkBox.setChecked(ischecked[position]);
        holder.checkBox.setText(name);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()) {
                    ischecked[position] = true;
                    onItemClick.onItemCheck(source);
                } else {
                    ischecked[position] = false;
                    onItemClick.onItemUncheck(source);
                }
            }
        });

        holder.checkBox.setChecked(ischecked[position]);

    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    public interface OnItemCheckListener {
        void onItemCheck(Source item);

        void onItemUncheck(Source item);
    }

    public class SourceViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textView;

        public SourceViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.content_source);
            checkBox = itemView.findViewById(R.id.checkbox_source);
        }

    }
}
