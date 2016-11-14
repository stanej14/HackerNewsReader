package cz.stanej14.hackernewsreader.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * HackerNews Item.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
public class Item {
    public static final String TAG = Item.class.getName();

    private int id;

    @SerializedName("by")
    private String author;

    @SerializedName("descendants")
    private int numberOfDescendants;

    private List<Integer> kids = new ArrayList<Integer>();

    private int score;

    private long time;

    private String title;

    private String type;

    private String url;

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public int getNumberOfDescendants() {
        return numberOfDescendants;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public int getScore() {
        return score;
    }

    public long getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
