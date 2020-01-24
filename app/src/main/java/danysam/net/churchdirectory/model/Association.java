package danysam.net.churchdirectory.model;

/**
 * Created by Dany on 18-01-2018.
 */

public class Association {

    private String name;
    private String designation;
    private int image;

    public Association(String name, String designation, int image) {
        this.name = name;
        this.designation = designation;
        this.image = image;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
