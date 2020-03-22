package mscc.net.churchdirectory.room.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by Dany on 11-03-2018.
 */

@Entity(tableName = "notification")
public class Notification {

    @PrimaryKey
    private int id;
    private String title;
    private String message;
    private String image;
    private String link;
    private String date;

    public Notification(int id, String title, String message, String image, String link, String date) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.image = image;
        this.link = link;
        this.date = date;
    }

    @Ignore
    public Notification() {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
