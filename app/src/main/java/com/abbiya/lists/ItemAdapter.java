package com.abbiya.lists;

import androidx.appcompat.app.AlertDialog;
import androidx.paging.PagedListAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class ItemAdapter extends PagedListAdapter<Item, ItemAdapter.ItemViewHolder> {

    private ItemViewModel viewModel;
    DateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");

    private static DiffUtil.ItemCallback<Item> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Item>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(Item oldItem, Item newItem) {
                    return oldItem.getUid() == newItem.getUid() && 0 == oldItem.getUpdatedAt().compareTo(newItem.getUpdatedAt());
                }

                @Override
                public boolean areContentsTheSame(Item oldItem, Item newItem) {
                    return Objects.equals(oldItem, newItem);
                }
            };

    protected ItemAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setViewModel(ItemViewModel viewModel) {
        this.viewModel = viewModel;
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

            itemViewHolder.itemView.setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    viewModel.delete(item);
                                }
                            });

                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.setTitle("Delete list");
                            builder.setMessage("Are you sure ?");

                            builder.create().show();

                            return true;
                        }
                    }
            );

            itemViewHolder.bindTo(item);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView content;
        private TextView touchedDate;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.content = itemView.findViewById(R.id.content);
            this.touchedDate = itemView.findViewById(R.id.touchedDate);
        }

        public void bindTo(Item item) {
            content.setText(item.getContent());
            touchedDate.setText(simpleDate.format(item.getUpdatedAt()));
        }
    }
}
