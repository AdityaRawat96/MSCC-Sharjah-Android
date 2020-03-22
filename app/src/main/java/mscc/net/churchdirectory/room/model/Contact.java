package mscc.net.churchdirectory.room.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Dany on 18-02-2018.
 */

@Entity(tableName = "contact")
public class Contact {

    @NonNull
    @PrimaryKey
    private String data = "";
    private int familyId;
    private String type;
    private String name;

    public Contact(int familyId, String type, String name, String data) {
        this.familyId = familyId;
        this.type = type;
        this.name = name;
        this.data = data;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
