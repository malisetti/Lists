package com.abbiya.lists;

import android.arch.paging.PagedListAdapter;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemAdapter extends PagedListAdapter<Item, ItemAdapter.ItemViewHolder> {

    private static DiffUtil.ItemCallback<Item> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Item>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(Item oldItem, Item newItem) {
                    return oldItem.getUid() == newItem.getUid() && 0 == oldItem.getUpdatedAt().compareTo(newItem.getUpdatedAt());
                }

                @Override
                public boolean areContentsTheSame(Item oldItem,
                                                  Item newItem) {
                    return oldItem.equals(newItem);
                }
            };

    protected ItemAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.recyclerview_item, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        final Item item = getItem(i);
        if (item != null) {
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextI = new Intent(v.getContext(), MainActivity.class);
                    nextI.putExtra(MainActivity.PARENT_ID, item.getUid());
                    v.getContext().startActivity(nextI);
                }
            });

            itemViewHolder.bindTo(item);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView.findViewById(R.id.textView);
        }

        public void bindTo(Item item) {
            itemView.setText(item.getContent());
        }
    }
}
