package cz.stanej14.hackernewsreader.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.ButterKnife;
import cz.stanej14.hackernewsreader.App;
import cz.stanej14.hackernewsreader.R;
import cz.stanej14.hackernewsreader.RxBus;
import cz.stanej14.hackernewsreader.ui.fragment.BestStoriesFeedFragment;

/**
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 */
public class MainActivity extends AppCompatActivity {

    final long MINUTE_MILLIS = 1000 * 60;

    @Inject
    RxBus rxBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        App.getAppComponent().inject(this);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.lay_main_container, new BestStoriesFeedFragment(), BestStoriesFeedFragment.TAG)
                    .commit();

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {

                @Override
                public void run() {
//                    rxBus.post(new RefreshBestStoriesFeedEvent());
                }
            };
            timer.schedule(task, MINUTE_MILLIS, MINUTE_MILLIS);
        }
    }
}
