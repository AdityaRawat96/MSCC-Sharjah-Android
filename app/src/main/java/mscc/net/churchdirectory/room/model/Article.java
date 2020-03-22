package mscc.net.churchdirectory.room.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Dany on 11-03-2018.
 */

@Entity(tableName = "article")
public class Article {

    public Article(int id, String title, String content, String image, String link, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.link = link;
        this.date = date;
    }

    @PrimaryKey
    private int id;
    private String title;
    private String content;
    private String image;
    private String link;
    private String date;


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
