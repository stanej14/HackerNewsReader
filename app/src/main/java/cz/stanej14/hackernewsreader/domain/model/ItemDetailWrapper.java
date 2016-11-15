package cz.stanej14.hackernewsreader.domain.model;

import java.util.List;

/**
 * Item with author and kids.
 *
 * @author Jan Stanek[jan.stanek@firma.seznam.cz]
 * @since {15/11/16}
 **/
public class ItemDetailWrapper {
    public static final String TAG = ItemDetailWrapper.class.getName();

    public final User author;
    public final List<Item> kids;
    public final Item item;

    public ItemDetailWrapper(User author, List<Item> kids, Item item) {
        this.author = author;
        this.kids = kids;
        this.item = item;
    }
}
