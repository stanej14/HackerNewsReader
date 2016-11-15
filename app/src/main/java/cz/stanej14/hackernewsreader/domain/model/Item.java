package cz.stanej14.hackernewsreader.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * HackerNews Item.
 * Created by Jan Stanek[jan.stanek@firma.seznam.cz] on {12.11.16}
 **/
@ParcelablePlease
public class Item implements Parcelable {
    public static final String TAG = Item.class.getName();

    int id;

    @SerializedName("by")
    String author;

    @SerializedName("descendants")
    int numberOfDescendants;

    int[] kids;

    int score;

    long time;

    String text;

    String title;

    String type;

    String url;

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public int getNumberOfDescendants() {
        return numberOfDescendants;
    }

    public int[] getKids() {
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

    public String getText() {
        return text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ItemParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        public Item createFromParcel(Parcel source) {
            Item target = new Item();
            ItemParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
