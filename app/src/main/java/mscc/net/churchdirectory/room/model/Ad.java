package mscc.net.churchdirectory.room.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Dany on 11-03-2018.
 */

@Entity(tableName = "ad")
public class Ad {

    @PrimaryKey
    private int id;
    private String description;
    private String imageSmall;
    private String imageLarge;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(String imageLarge) {
        this.imageLarge = imageLarge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ad(int id, String description, String imageSmall, String imageLarge, String name) {

        this.id = id;
        this.description = description;
        this.imageSmall = imageSmall;
        this.imageLarge = imageLarge;
        this.name = name;
    }

}
