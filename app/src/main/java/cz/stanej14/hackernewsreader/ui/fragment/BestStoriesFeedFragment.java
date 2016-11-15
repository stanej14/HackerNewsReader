package cz.stanej14.hackernewsreader.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.stanej14.hackernewsreader.R;
import cz.stanej14.hackernewsreader.domain.model.Item;
import cz.stanej14.hackernewsreader.mvp.presenter.BestStoriesFeedPresenter;
import cz.stanej14.hackernewsreader.mvp.presenter.StoryDetailPresenter;
import cz.stanej14.hackernewsreader.mvp.view.IBestStoriesFeedView;
import cz.stanej14.hackernewsreader.ui.activity.StoryDetailActivity;
import cz.stanej14.hackernewsreader.ui.adapter.ItemAdapter;
import cz.stanej14.hackernewsreader.ui.fragment.base.BaseRetryNucleusFragment;
import cz.stanej14.hackernewsreader.utils.UiUtils;
import nucleus.factory.RequiresPresenter;

/**
 * Fragment displaying feed of best stories.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {13.11.16}
 **/
@RequiresPresenter(BestStoriesFeedPresenter.class)
public class BestStoriesFeedFragment extends BaseRetryNucleusFragment<BestStoriesFeedPresenter> implements IBestStoriesFeedView {
    public static final String TAG = BestStoriesFeedFragment.class.getName();

    @BindView(R.id.text_feed_error)
    TextView textError;
    @BindView(R.id.recycler_feed)
    RecyclerView recycler;
    @BindView(R.id.swipe_feed)
    SwipeRefreshLayout swipeRefreshLayout;
    private ItemAdapter itemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 0;
                outRect.top = UiUtils.dpToPx(getActivity(), 1);
                outRect.right = 0;
                outRect.left = 0;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getPresenter().refresh();
            if (itemAdapter != null) {
                itemAdapter.clearItems();
            }
            swipeRefreshLayout.setRefreshing(true);
        });
    }

    @Override
    public void showError() {
        recycler.setVisibility(View.GONE);
        textError.setVisibility(View.VISIBLE);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void openDetail(@NonNull Item item) {
        Intent intent = new Intent(getActivity(), StoryDetailActivity.class);
        intent.putExtras(StoryDetailPresenter.createBundle(item));
        startActivity(intent);
    }

    @Override
    public void showBestStoryItem(@NonNull Item item) {
        if (itemAdapter == null) {
            itemAdapter = new ItemAdapter(getPresenter()::onItemClicked);
            recycler.setAdapter(itemAdapter);
        }
        itemAdapter.addItem(item);
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        recycler.setVisibility(View.VISIBLE);
        textError.setVisibility(View.GONE);
    }
}
