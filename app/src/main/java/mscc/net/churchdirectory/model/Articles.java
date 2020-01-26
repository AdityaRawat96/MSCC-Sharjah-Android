package mscc.net.churchdirectory.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dany on 02-01-2018.
 */


public class Articles {

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

    public class Response {

        @SerializedName("articles")
        @Expose
        private List<Article> articles = null;

        public List<Article> getArticles() {
            return articles;
        }

        public void setArticles(List<Article> articles) {
            this.articles = articles;
        }

    }

    public static class Article implements Parcelable{

        public Article(String id, String title, String content, String image, String date) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.image = image;
            this.date = date;
        }

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("date")
        @Expose
        private String date;

        public Article(Parcel in) {
            id = in.readString();
            title = in.readString();
            content = in.readString();
            image = in.readString();
            date = in.readString();
        }

        public static final Creator<Article> CREATOR = new Creator<Article>() {
            @Override
            public Article createFromParcel(Parcel in) {
                return new Article(in);
            }

            @Override
            public Article[] newArray(int size) {
                return new Article[size];
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
            dest.writeString(content);
            dest.writeString(image);
            dest.writeString(date);
        }
    }

}
