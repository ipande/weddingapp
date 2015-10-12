package desipride.socialshaadi.shadidata;

/**
 * Created by parth.mehta on 10/10/15.
 */
public class NewsFeedItem {
    private Long _id;
    private String url;
    private String caption;
    private NewsFeedItemType mediaType;
    public enum NewsFeedItemType {IMAGE,VIDEO,UNKNOWN};


    public NewsFeedItem() {}

    public NewsFeedItem(long id,String url,String caption, NewsFeedItemType mediaType) {
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

    public NewsFeedItemType getMediaType() {
        return mediaType;
    }

    public void setMediaType(NewsFeedItemType mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ Id:").append(_id);
        builder.append(", url:").append(url);
        builder.append(", caption:").append(caption);
        builder.append(", mediaType:");
        if(mediaType == NewsFeedItemType.IMAGE) {
            builder.append("Image");
        }else if(mediaType == NewsFeedItemType.VIDEO) {
            builder.append("Video");
        }
        builder.append("}");
        return builder.toString();
    }
}
