package cz.stanej14.hackernewsreader.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.stanej14.hackernewsreader.R;
import cz.stanej14.hackernewsreader.domain.model.Item;

/**
 * RecyclerView adapter for {@link Item}.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private final List<Item> items;

    public CommentAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_item_author)
        TextView textAuthor;
        @BindView(R.id.text_item_text)
        TextView textText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Item item) {
            textAuthor.setText(item.getAuthor());
            textText.setText(item.getText());
        }
    }
}
