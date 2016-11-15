package cz.stanej14.hackernewsreader.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.stanej14.hackernewsreader.R;
import cz.stanej14.hackernewsreader.domain.model.ItemDetailWrapper;
import cz.stanej14.hackernewsreader.mvp.presenter.StoryDetailPresenter;
import cz.stanej14.hackernewsreader.mvp.view.IStoryDetailView;
import cz.stanej14.hackernewsreader.ui.activity.base.BaseRetryNucleusActivity;
import cz.stanej14.hackernewsreader.ui.adapter.CommentAdapter;
import cz.stanej14.hackernewsreader.utils.UiUtils;
import nucleus.factory.RequiresPresenter;

/**
 * Activity class for displaying Item Detail.
 *
 * @author Jan Stanek[jan.stanek@firma.seznam.cz]
 * @since {15/11/16}
 **/
@RequiresPresenter(StoryDetailPresenter.class)
public class StoryDetailActivity extends BaseRetryNucleusActivity<StoryDetailPresenter>
        implements IStoryDetailView {
    public static final String TAG = StoryDetailActivity.class.getName();

    @BindView(R.id.text_story_detail_title)
    TextView textTitle;
    @BindView(R.id.text_story_detail_url)
    TextView textContent;
    @BindView(R.id.text_story_detail_score)
    TextView textScore;
    @BindView(R.id.recycler_story_detail_comments)
    RecyclerView recyclerComments;
    @BindView(R.id.text_story_detail_author)
    TextView textAuthor;
    @BindView(R.id.text_story_detail_author_karma)
    TextView textAuthorKarma;
    @BindView(R.id.progress_story_detail)
    FrameLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().init(getIntent().getExtras());
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    protected View createContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_story_detail, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void showData(@NonNull ItemDetailWrapper wrapper) {
        progress.setVisibility(View.GONE);
        textTitle.setText(wrapper.item.getTitle());
        if (TextUtils.isEmpty(wrapper.item.getUrl())) {
            textContent.setText(wrapper.item.getText());
        } else {
            textContent.setText(wrapper.item.getUrl());
            textContent.setOnClickListener(v -> openUrl(wrapper.item.getUrl()));
        }
        textScore.setText(getString(R.string.story_detail_score, wrapper.item.getScore()));
        textAuthor.setText(getString(R.string.story_detail_author, wrapper.author.id));
        textAuthorKarma.setText(getString(R.string.story_detail_score, wrapper.author.karma));

        recyclerComments.setLayoutManager(new LinearLayoutManager(this));
        recyclerComments.setAdapter(new CommentAdapter(wrapper.kids));
        recyclerComments.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 0;
                outRect.top = UiUtils.dpToPx(StoryDetailActivity.this, 1);
                outRect.right = 0;
                outRect.left = 0;
            }
        });
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
    }

    public void openUrl(@NonNull String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
