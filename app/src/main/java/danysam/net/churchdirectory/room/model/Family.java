package danysam.net.churchdirectory.room.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Dany on 18-02-2018.
 */

@Entity(tableName = "family", indices = {@Index(value="id", unique=true)})
public class Family {

    @PrimaryKey
    private int id;
    private String name;
    private String address;
    private String prayerGroup;
    private String permanentAddress;
    private String homeParish;
    private String image;
    private String emergencyContact;
    private String dom;

    private Boolean visible;

    public Family(int id, String name, String address, String prayerGroup, String permanentAddress, String homeParish, String image, String emergencyContact, String dom, Boolean visible) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.prayerGroup = prayerGroup;
        this.permanentAddress = permanentAddress;
        this.homeParish = homeParish;
        this.image = image;
        this.emergencyContact = emergencyContact;
        this.dom = dom;
        this.visible = visible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrayerGroup() {
        return prayerGroup;
    }

    public void setPrayerGroup(String prayerGroup) {
        this.prayerGroup = prayerGroup;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getHomeParish() {
        return homeParish;
    }

    public void setHomeParish(String homeParish) {
        this.homeParish = homeParish;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
