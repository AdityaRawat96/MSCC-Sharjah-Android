package mscc.net.churchdirectory.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Dany on 18-02-2018.
 */

@Entity(tableName = "member")
public class Member {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int familyId;
    private String relation;
    private String dob;
    private String bloodGroup;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
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

    public String getName() {
        if (name == null) return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Member(int id, int familyId, String relation, String dob, String bloodGroup, String name) {

        this.id = id;
        this.familyId = familyId;
        this.relation = relation;
        this.dob = dob;
        this.bloodGroup = bloodGroup;
        this.name = name;
    }
}
