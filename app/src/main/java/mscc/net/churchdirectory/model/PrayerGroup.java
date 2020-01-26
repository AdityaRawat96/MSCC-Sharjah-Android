package mscc.net.churchdirectory.model;

/**
 * Created by Dany on 18-01-2018.
 */

public class PrayerGroup {
    private String name;
    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public PrayerGroup(String name, int image) {
        this.name = name;
        this.image = image;
    }
}
