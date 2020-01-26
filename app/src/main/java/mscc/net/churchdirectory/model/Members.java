package mscc.net.churchdirectory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dany on 26-01-2018.
 */

public class Members {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("familyId")
    @Expose
    private Integer familyId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("relation")
    @Expose
    private String relation;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

}
