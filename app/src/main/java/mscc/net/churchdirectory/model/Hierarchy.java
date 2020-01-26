package mscc.net.churchdirectory.model;

public class Hierarchy {

    public String hierarchyId;
    public String hierarchyName;
    public String hierarchyDesignation;
    public String hierarchyRank;
    public String hierarchyImage;
    public String hierarchyTimestamp;


    public Hierarchy(String id, String name, String designation, String rank, String image, String timestamp) {
        hierarchyId = id;
        hierarchyName = name;
        hierarchyDesignation = designation;
        hierarchyRank = rank;
        hierarchyImage = image;
        hierarchyTimestamp = timestamp;
    }
    public Hierarchy() {
    }

    public void setHierarchyId(String hierarchyId) {
        this.hierarchyId = hierarchyId;
    }

    public void setHierarchyName(String hierarchyName) {
        this.hierarchyName = hierarchyName;
    }

    public void setHierarchyDesignation(String hierarchyDesignation) {
        this.hierarchyDesignation = hierarchyDesignation;
    }

    public void setHierarchyRank(String hierarchyRank) {
        this.hierarchyRank = hierarchyRank;
    }

    public void setHierarchyImage(String hierarchyImage) {
        this.hierarchyImage = hierarchyImage;
    }

    public void setHierarchyTimestamp(String hierarchyTimestamp) {
        this.hierarchyTimestamp = hierarchyTimestamp;
    }

    public String getHierarchyId() {
        return hierarchyId;
    }

    public String getHierarchyName() {
        return hierarchyName;
    }

    public String getHierarchyDesignation() {
        return hierarchyDesignation;
    }

    public String getHierarchyRank() {
        return hierarchyRank;
    }

    public String getHierarchyImage() {
        return hierarchyImage;
    }

    public String getHierarchyTimestamp() {
        return hierarchyTimestamp;
    }
}
