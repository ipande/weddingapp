package desipride.socialshaadi.shadidata;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

import desipride.socialshaadi.desipride.socialshaadi.utils.Constants;

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
    private String food;
    private String eventDetails;

    public Event(String title,Date startTime,Date endTime,String addressTitle, String addressDetails, Uri addressUri, int thumbnail,int coverPage, String food, String eventDetails){
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.addressTitle = addressTitle;
        this.addressDetails = addressDetails;
        this.addressUri = addressUri;
        this.thumbnail = thumbnail;
        this.coverPage = coverPage;
        this.food = food;
        this.eventDetails = eventDetails;
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

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getDateTimeString() {
        if(startTime == null)
            return null;

        StringBuilder buffer = new StringBuilder();
        buffer.append(new SimpleDateFormat(Constants.DATE_FORMAT).format(startTime));
        if(endTime == null) {
            buffer.append(" onwards");
        } else {
            buffer.append(new SimpleDateFormat(Constants.TIME_FORMAT).format(endTime));
        }
        return buffer.toString();
    }

    public String getEventDateString() {
        if(startTime != null) {
            return new SimpleDateFormat(Constants.DATE_FORMAT).format(startTime);
        }
        return null;
    }
}
