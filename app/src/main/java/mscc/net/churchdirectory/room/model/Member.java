package mscc.net.churchdirectory.room.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

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
    private int priority;

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
        if(relation.equalsIgnoreCase("Family Head")){
            setPriority(1);
        }
        else if(relation.equalsIgnoreCase("Grandfather")){
            setPriority(2);
        }
        else if(relation.equalsIgnoreCase("Grandmother")){
            setPriority(3);
        }
        else if(relation.equalsIgnoreCase("Father")){
            setPriority(4);
        }
        else if(relation.equalsIgnoreCase("Mother")){
            setPriority(5);
        }
        else if(relation.equalsIgnoreCase("Uncle")){
            setPriority(6);
        }
        else if(relation.equalsIgnoreCase("Aunt")){
            setPriority(7);
        }
        else if(relation.equalsIgnoreCase("Husband")){
            setPriority(8);
        }
        else if(relation.equalsIgnoreCase("Wife")){
            setPriority(9);
        }
        else if(relation.equalsIgnoreCase("Brother")){
            setPriority(10);
        }
        else if(relation.equalsIgnoreCase("Sister")){
            setPriority(11);
        }
        else if(relation.equalsIgnoreCase("Son")){
            setPriority(12);
        }
        else if(relation.equalsIgnoreCase("Daughter")){
            setPriority(13);
        }
        else if(relation.equalsIgnoreCase("Son-in-law")){
            setPriority(14);
        }
        else if(relation.equalsIgnoreCase("Daughter-in-law")){
            setPriority(15);
        }
        else if(relation.equalsIgnoreCase("Grand son")){
            setPriority(16);
        }
        else if(relation.equalsIgnoreCase("Grand daughter")){
            setPriority(17);
        }
        else if(relation.equalsIgnoreCase("Relative")){
            setPriority(18);
        }
        else{
            setPriority(19);
        }
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    @Ignore
    public Member(int id, int familyId, String relation, String dob, String bloodGroup, String name, int priority) {

        this.id = id;
        this.familyId = familyId;
        this.relation = relation;
        this.dob = dob;
        this.bloodGroup = bloodGroup;
        this.name = name;
        this.priority = priority;
    }
}
