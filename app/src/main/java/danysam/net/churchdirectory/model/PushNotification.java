package danysam.net.churchdirectory.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dany on 01-03-2018.
 */


public class PushNotification implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("isBirthday")
    @Expose
    private Boolean isBirthday;
    @SerializedName("isAnniversary")
    @Expose
    private Boolean isAnniversary;

    public String getTitle() {
        if (title == null) title = "You have a message";
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        if (message == null) message = "Click here to know more";
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        if (image == null) image = "";
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getBirthday() {
        if (isBirthday == null) isBirthday = false;
        return isBirthday;
    }

    public void setBirthday(Boolean birthday) {
        isBirthday = birthday;
    }

    public Boolean getAnniversary() {
        if (isAnniversary == null) isAnniversary = false;
        return isAnniversary;
    }

    public void setAnniversary(Boolean anniversary) {
        isAnniversary = anniversary;
    }

    public Boolean getArticle() {
        if (isArticle == null) isArticle = false;
        return isArticle;
    }

    public void setArticle(Boolean article) {
        isArticle = article;
    }

    public Boolean getNotification() {
        if (isNotification == null) isNotification = false;
        return isNotification;
    }

    public void setNotification(Boolean notification) {
        isNotification = notification;
    }

    public PushNotification(String title, String message, String image, Boolean isBirthday, Boolean isAnniversary, Boolean isArticle, Boolean isNotification) {

        this.title = title;
        this.message = message;
        this.image = image;
        this.isBirthday = isBirthday;
        this.isAnniversary = isAnniversary;
        this.isArticle = isArticle;
        this.isNotification = isNotification;
    }

    @SerializedName("isArticle")

    @Expose
    private Boolean isArticle;
    @SerializedName("isNotification")
    @Expose
    private Boolean isNotification;

    protected PushNotification(Parcel in) {
        title = in.readString();
        message = in.readString();
        image = in.readString();
        byte tmpIsBirthday = in.readByte();
        isBirthday = tmpIsBirthday == 0 ? null : tmpIsBirthday == 1;
        byte tmpIsAnniversary = in.readByte();
        isAnniversary = tmpIsAnniversary == 0 ? null : tmpIsAnniversary == 1;
        byte tmpIsArticle = in.readByte();
        isArticle = tmpIsArticle == 0 ? null : tmpIsArticle == 1;
        byte tmpIsNotification = in.readByte();
        isNotification = tmpIsNotification == 0 ? null : tmpIsNotification == 1;
    }

    public static final Creator<PushNotification> CREATOR = new Creator<PushNotification>() {
        @Override
        public PushNotification createFromParcel(Parcel in) {
            return new PushNotification(in);
        }

        @Override
        public PushNotification[] newArray(int size) {
            return new PushNotification[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(image);
        dest.writeByte((byte) (isBirthday == null ? 0 : isBirthday ? 1 : 2));
        dest.writeByte((byte) (isAnniversary == null ? 0 : isAnniversary ? 1 : 2));
        dest.writeByte((byte) (isArticle == null ? 0 : isArticle ? 1 : 2));
        dest.writeByte((byte) (isNotification == null ? 0 : isNotification ? 1 : 2));
    }
}