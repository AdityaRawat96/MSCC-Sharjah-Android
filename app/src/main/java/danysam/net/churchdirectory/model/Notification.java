package danysam.net.churchdirectory.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dany on 28-02-2018.
 */

public class Notification implements Parcelable{

    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("count")
    @Expose
    private Integer count;

    protected Notification(Parcel in) {
        byte tmpSuccess = in.readByte();
        success = tmpSuccess == 0 ? null : tmpSuccess == 1;
        error = in.readString();
        message = in.readString();
        if (in.readByte() == 0) {
            count = null;
        } else {
            count = in.readInt();
        }
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (success == null ? 0 : success ? 1 : 2));
        dest.writeString(error);
        dest.writeString(message);
        if (count == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(count);
        }
    }

    public static class Notification_ implements Parcelable{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;

        public Notification_(String id, String title, String message, String image, String date) {
            this.id = id;
            this.title = title;
            this.message = message;
            this.image = image;
            this.date = date;
        }

        @SerializedName("message")

        @Expose
        private String message;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("date")
        @Expose
        private String date;

        protected Notification_(Parcel in) {
            id = in.readString();
            title = in.readString();
            message = in.readString();
            image = in.readString();
            date = in.readString();
        }

        public static final Creator<Notification_> CREATOR = new Creator<Notification_>() {
            @Override
            public Notification_ createFromParcel(Parcel in) {
                return new Notification_(in);
            }

            @Override
            public Notification_[] newArray(int size) {
                return new Notification_[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(title);
            dest.writeString(message);
            dest.writeString(image);
            dest.writeString(date);
        }
    }

    public class Response {

        @SerializedName("notifications")
        @Expose
        private List<Notification_> notifications = null;

        public List<Notification_> getNotifications() {
            return notifications;
        }

        public void setNotifications(List<Notification_> notifications) {
            this.notifications = notifications;
        }

    }
}


