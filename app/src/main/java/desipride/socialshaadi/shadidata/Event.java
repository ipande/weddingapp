package desipride.socialshaadi.shadidata;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by parth.mehta on 9/15/15.
 */
public class Event {
    private String title;
    private Date startTime;
    private Date endTime;
    private String addressTitle;
    private String addressDetails;
    private Uri addressUri;
    private int thumbnail;
    private int coverPage;

    public Event(String title,Date startTime,Date endTime,String addressTitle, String addressDetails, Uri addressUri, int thumbnail,int coverPage){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.addressTitle = addressTitle;
        this.addressDetails = addressDetails;
        this.addressUri = addressUri;
        this.thumbnail = thumbnail;
        this.coverPage = coverPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAddressTitle() {
        return addressTitle;
    }

    public void setAddressTitle(String addressTitle) {
        this.addressTitle = addressTitle;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public Uri getAddressUri() {
        return addressUri;
    }

    public void setAddressUri(Uri addressUri) {
        this.addressUri = addressUri;
    }


    public int getCoverPage() {
        return coverPage;
    }

    public void setCoverPage(int coverPage) {
        this.coverPage = coverPage;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }


    public String getDateTimeString() {
        if(startTime == null)
            return null;

        StringBuilder buffer = new StringBuilder();
        buffer.append(new SimpleDateFormat("EEE, d MMM yyyy, hh:mm a").format(startTime));
        if(endTime == null) {
            buffer.append(" onwards");
        } else {
            buffer.append(new SimpleDateFormat("to hh:mm a").format(endTime));
        }
        return buffer.toString();
    }

    public String getEventDateString() {
        if(startTime != null) {
            return new SimpleDateFormat("EEE, d MMM yyyy").format(startTime);
        } else {
            return null;
        }
    }
}
