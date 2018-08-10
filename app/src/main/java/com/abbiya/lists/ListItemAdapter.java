package com.abbiya.lists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ItemViewHolder> {

    private List<Item> mListItems;
    private IItemClickListener mItemListener;


    public ListItemAdapter(List<Item> items, IItemClickListener itemClickListener) {
        mListItems = items;
        mItemListener = itemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Item item = mListItems.get(i);
        itemViewHolder.mTitleView.setText(item.getContent());
    }

    public void addItem(Item item) {
        mListItems.add(item);
        notifyDataSetChanged();     // TODO check, pass position as well?
    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mTitleView;

        ItemViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = mView.findViewById(R.id.tv_list_item);
        }
    }
}
