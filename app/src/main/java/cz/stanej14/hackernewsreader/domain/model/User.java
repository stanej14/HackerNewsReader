package cz.stanej14.hackernewsreader.domain.model;

import java.util.List;

/**
 * User POJO.
 *
 * @author Jan Stanek[jan.stanek@firma.seznam.cz]
 * @since {15/11/16}
 **/
public class User {
    public static final String TAG = User.class.getName();

    public final String about;
    public final long created;
    public final int delay;
    public final String id;
    public final int karma;
    public final List<Integer> submitted;

    public User(String about, long created, int delay, String id, int karma, List<Integer> submitted) {
        this.about = about;
        this.created = created;
        this.delay = delay;
        this.id = id;
        this.karma = karma;
        this.submitted = submitted;
    }
}
