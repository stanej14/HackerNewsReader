package cz.stanej14.hackernewsreader.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.Arrays;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (numberOfDescendants != item.numberOfDescendants) return false;
        if (score != item.score) return false;
        if (time != item.time) return false;
        if (author != null ? !author.equals(item.author) : item.author != null) return false;
        if (!Arrays.equals(kids, item.kids)) return false;
        if (text != null ? !text.equals(item.text) : item.text != null) return false;
        if (title != null ? !title.equals(item.title) : item.title != null) return false;
        if (type != null ? !type.equals(item.type) : item.type != null) return false;
        return url != null ? url.equals(item.url) : item.url == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + numberOfDescendants;
        result = 31 * result + Arrays.hashCode(kids);
        result = 31 * result + score;
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
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
