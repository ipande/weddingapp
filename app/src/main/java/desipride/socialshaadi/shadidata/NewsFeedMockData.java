package desipride.socialshaadi.shadidata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parth.mehta on 10/10/15.
 */
public class NewsFeedMockData {
    private static String urls[] = {
            "http://10.0.3.2:5000/static/img/newsfeed/1444364810.07.jpg",
            "http://10.0.3.2:5000/static/img/newsfeed/1444364828.78.jpg"
    };
    private static String captions[] = {
            "Preparations in full swing, we are very excited",
            "Sneak Peak on the decoration at the evening"
    };
    public static List<NewsFeedItem> getNewsFeedItems() {
        List<NewsFeedItem> list = new ArrayList<NewsFeedItem>();
        for(int i=0; i< urls.length;i++) {
            list.add(new NewsFeedItem(i,urls[i],captions[i], NewsFeedItem.NewsFeedItemType.IMAGE));
        }
        return list;
    }
}
