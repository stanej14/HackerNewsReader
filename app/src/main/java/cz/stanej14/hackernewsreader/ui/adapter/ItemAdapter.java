package cz.stanej14.hackernewsreader.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.stanej14.hackernewsreader.R;
import cz.stanej14.hackernewsreader.domain.model.Item;

/**
 * RecyclerView adapter for {@link Item}.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> items;
    private OnItemClickListener itemClickListener;

    public ItemAdapter(@NonNull OnItemClickListener listener) {
        this.items = new ArrayList<>();
        this.itemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Add item to the adapter.
     *
     * @param item Item.
     */
    public void addItem(@NonNull Item item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    /**
     * Clear items.
     */
    public void clearItems() {
        items.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final OnItemClickListener itemClickListener;
        @BindView(R.id.text_item_author)
        TextView textAuthor;
        @BindView(R.id.text_item_title)
        TextView textTitle;

        public ViewHolder(@NonNull View itemView, @NonNull OnItemClickListener listener) {
            super(itemView);
            this.itemClickListener = listener;
            ButterKnife.bind(this, itemView);
        }

        public void bind(@NonNull Item item) {
            itemView.setOnClickListener(v -> itemClickListener.onItemClicked(item.getUrl()));
            textAuthor.setText(item.getAuthor());
            textTitle.setText(item.getTitle());
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(@NonNull String url);
    }
}
