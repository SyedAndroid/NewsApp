package com.udacity.syed.newsapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by syed on 2017-06-27.
 */


public class MySourceItemRecyclerViewAdapter extends RecyclerView.Adapter<MySourceItemRecyclerViewAdapter.SourceViewHolder> {
    OnItemCheckListener onItemClick;
    boolean[] ischecked;
    List<Source> sources;
    Context context;

    public MySourceItemRecyclerViewAdapter(Context context, List<Source> sources, @NonNull OnItemCheckListener onItemCheckListener) {
        this.sources = sources;
        this.context = context;
        this.onItemClick = onItemCheckListener;
        ischecked = new boolean[sources.size()];
        for (int i = 0; i < sources.size(); i++)
            ischecked[i] = false;

    }

    @Override
    public SourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context2 = parent.getContext();
        int layoutForListItem = R.layout.fragment_sourceitem;
        LayoutInflater layoutInflater = LayoutInflater.from(context2);
        View view = layoutInflater.inflate(layoutForListItem, parent, false);
        return new SourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SourceViewHolder holder, final int position) {

        final Source source = sources.get(position);

        String name = source.getName();
        holder.textView.setVisibility(View.GONE);
        holder.checkBox.setChecked(ischecked[position]);
        String pic = source.getId().replaceAll("-", "_");

        int drawableId = context.getResources().getIdentifier(pic, "drawable", context.getPackageName());
        int id = context.getResources().getIdentifier("news", "drawable", context.getPackageName());

        try {
            Class res = R.drawable.class;
            Field field = res.getField(pic);
            drawableId = field.getInt(null);
        } catch (Exception e) {
            Log.e("MyTag", "Failure to get drawable id.", e);
        }
        if (source.getStatus() == 2) {
            ischecked[position] = true;
        }
        if (drawableId != 0) {
            Picasso.with(context).load(drawableId).into(holder.sourceImage);
        } else
            Picasso.with(context).load(id).into(holder.sourceImage);

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
        ImageView sourceImage;

        public SourceViewHolder(View itemView) {
            super(itemView);
            sourceImage = itemView.findViewById(R.id.source_image);
            textView = itemView.findViewById(R.id.content_source);
            checkBox = itemView.findViewById(R.id.checkbox_source);
        }

    }
}
