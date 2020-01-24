package danysam.net.churchdirectory.model;

public class Committee {

    public String committeeId;
    public String committeeName;
    public String committeeImage;
    public String committeeTimestamp;
    public String committeeRank;

    public Committee(String id, String name, String image, String timestamp, String rank){
        committeeId = id;
        committeeName = name;
        committeeImage = image;
        committeeTimestamp = timestamp;
        committeeRank = rank;
    }

    public Committee(){}

    public String getCommitteeId() {
        return committeeId;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public String getCommitteeImage() {
        return committeeImage;
    }

    public String getCommitteeTimestamp() {
        return committeeTimestamp;
    }

    public String getCommitteeRank() {
        return committeeRank;
    }

    public void setCommitteeId(String committeeId) {
        this.committeeId = committeeId;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public void setCommitteeImage(String committeeImage) {
        this.committeeImage = committeeImage;
    }

    public void setCommitteeTimestamp(String committeeTimestamp) {
        this.committeeTimestamp = committeeTimestamp;
    }

    public void setCommitteeRank(String committeeRank) {
        this.committeeRank = committeeRank;
    }
}
