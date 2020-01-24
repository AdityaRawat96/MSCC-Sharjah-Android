package danysam.net.churchdirectory.model;

public class CommitteeMember {

    public String committeeMemberId;
    public String committeeMemberName;
    public String committeeMemberImage;
    public String committeeMemberDesignation;
    public String committeeMemberMemberOf;
    public String committeeMemberTimestamp;
    public String committeeMemberRank;
    public String executiveMember;

    public CommitteeMember(String id, String name, String image, String designation, String memberOf, String timestamp, String rank, String executive){
        committeeMemberId = id;
        committeeMemberName = name;
        committeeMemberImage = image;
        committeeMemberDesignation = designation;
        committeeMemberMemberOf = memberOf;
        committeeMemberTimestamp = timestamp;
        committeeMemberRank = rank;
        executiveMember = executive;
    }

    public CommitteeMember(){}

    public String getCommitteeMemberId() {
        return committeeMemberId;
    }

    public String getCommitteeMemberName() {
        return committeeMemberName;
    }

    public String getCommitteeMemberImage() {
        return committeeMemberImage;
    }

    public String getCommitteeMemberTimestamp() {
        return committeeMemberTimestamp;
    }

    public String getCommitteeMemberRank() {
        return committeeMemberRank;
    }

    public String getCommitteeMemberDesignation() {
        return committeeMemberDesignation;
    }

    public String getCommitteeMemberMemberOf() {
        return committeeMemberMemberOf;
    }

    public String getExecutiveMember() {
        return executiveMember;
    }

    public void setCommitteeMemberId(String committeeMemberId) {
        this.committeeMemberId = committeeMemberId;
    }

    public void setCommitteeMemberName(String committeeMemberName) {
        this.committeeMemberName = committeeMemberName;
    }

    public void setCommitteeMemberImage(String committeeMemberImage) {
        this.committeeMemberImage = committeeMemberImage;
    }

    public void setCommitteeMemberTimestamp(String committeeMemberTimestamp) {
        this.committeeMemberTimestamp = committeeMemberTimestamp;
    }

    public void setCommitteeMemberRank(String committeeMemberRank) {
        this.committeeMemberRank = committeeMemberRank;
    }

    public void setCommitteeMemberDesignation(String committeeMemberDesignation) {
        this.committeeMemberDesignation = committeeMemberDesignation;
    }

    public void setCommitteeMemberMemberOf(String committeeMemberMemberOf) {
        this.committeeMemberMemberOf = committeeMemberMemberOf;
    }

    public void setExecutiveMember(String executiveMember) {
        this.executiveMember = executiveMember;
    }
}
