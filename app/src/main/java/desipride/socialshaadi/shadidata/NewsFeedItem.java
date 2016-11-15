package desipride.socialshaadi.shadidata;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by parth.mehta on 10/10/15.
 */
@Parcel
public class NewsFeedItem {
    public Long _id;
    public String url;
    public String caption;
    public String mediaType;
    public int width;
    public int height;


    public NewsFeedItem() {}

    public NewsFeedItem(long id,String url,String caption, String mediaType) {
        _id = id;
        this.url = url;
        this.caption = caption;
        this.mediaType = mediaType;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        this._id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public String getDimentions() {
        return mediaType;
    }

    public void setDimentions(String dimentions) {
        this.mediaType = dimentions;
        try {
            JSONObject dimentionsJson = new JSONObject(dimentions);
            height = dimentionsJson.getInt("height");
            width = dimentionsJson.getInt("width");
        } catch (JSONException e) {
            Log.e(NewsFeedItem.class.getSimpleName(),"" + e);

        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ Id:").append(_id);
        builder.append(", url:").append(url);
        builder.append(", caption:").append(caption);
        builder.append(", mediaType:").append(mediaType);
        builder.append("}");
        return builder.toString();
    }

}
