package com.abbiya.lists;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Item> mItems; // cache
    ItemListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        if (mItems != null) {
            final Item current = mItems.get(i);
            itemViewHolder.itemView.setText(current.getContent());

            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextI = new Intent(v.getContext(), MainActivity.class);
                    nextI.putExtra(MainActivity.PARENT_ID, current.getUid());
                    v.getContext().startActivity(nextI);
                }
            });
        } else {
            itemViewHolder.itemView.setText("No items");
        }
    }

    void setItems(List<Item> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mItems != null) {
            return mItems.size();
        }

        return 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemView;

        private ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView.findViewById(R.id.textView);
        }
    }
}
