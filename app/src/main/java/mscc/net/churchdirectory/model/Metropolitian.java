package mscc.net.churchdirectory.model;

public class Metropolitian {

    public String metropolitianId;
    public String metropolitianName;
    public String metropolitianDesignation;
    public String metropolitianRank;
    public String metropolitianImage;
    public String metropolitianTimestamp;


    public Metropolitian(String id, String name, String designation, String rank, String image, String timestamp) {
        metropolitianId = id;
        metropolitianName = name;
        metropolitianDesignation = designation;
        metropolitianRank = rank;
        metropolitianImage = image;
        metropolitianTimestamp = timestamp;
    }
    public Metropolitian() {
    }

    public void setMetropolitianId(String metropolitianId) {
        this.metropolitianId = metropolitianId;
    }

    public void setMetropolitianName(String metropolitianName) {
        this.metropolitianName = metropolitianName;
    }

    public void setMetropolitianDesignation(String metropolitianDesignation) {
        this.metropolitianDesignation = metropolitianDesignation;
    }

    public void setMetropolitianRank(String metropolitianRank) {
        this.metropolitianRank = metropolitianRank;
    }

    public void setMetropolitianImage(String metropolitianImage) {
        this.metropolitianImage = metropolitianImage;
    }

    public void setMetropolitianTimestamp(String metropolitianTimestamp) {
        this.metropolitianTimestamp = metropolitianTimestamp;
    }

    public String getMetropolitianId() {
        return metropolitianId;
    }

    public String getMetropolitianName() {
        return metropolitianName;
    }

    public String getMetropolitianDesignation() {
        return metropolitianDesignation;
    }

    public String getMetropolitianRank() {
        return metropolitianRank;
    }

    public String getMetropolitianImage() {
        return metropolitianImage;
    }

    public String getMetropolitianTimestamp() {
        return metropolitianTimestamp;
    }
}
