package cz.stanej14.hackernewsreader.ui.view;

import android.content.Context;
import android.view.View;

/**
 * TODO add class description
 *
 * @author Jan Stanek[jan.stanek@firma.seznam.cz]
 * @since {15/11/16}
 **/
public class LambdaView extends View {
    public static final String TAG = LambdaView.class.getName();

    public LambdaView(Context context) {
        super(context);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                doOnClick(view);
            }
        });

        setOnClickListener(view -> doOnClick(view));

        setOnClickListener(this::doOnClick);
    }

    void doOnClick(View v) {

    }
}
