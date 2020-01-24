package danysam.net.churchdirectory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dany on 18-02-2018.
 */

public class Directory {

    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public static class Contact {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("data")
        @Expose
        private String data;

        public Contact(String type, String name, String data) {
            this.type = type;
            this.name = name;
            this.data = data;
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

    public static class Family {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("prayerGroup")
        @Expose
        private String prayerGroup;
        @SerializedName("permanentAddress")
        @Expose
        private String permanentAddress;
        @SerializedName("homeParish")
        @Expose
        private String homeParish;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("emergencyContact")
        @Expose
        private String emergencyContact;
        @SerializedName("dom")
        @Expose
        private String dom;
        @SerializedName("visible")
        @Expose
        private Boolean visible;
        @SerializedName("members")
        @Expose
        private List<Member> members = null;
        @SerializedName("contacts")
        @Expose
        private List<Contact> contacts = null;

        public Family(int id, String name, String address, String prayerGroup, String permanentAddress, String homeParish, String image, String emergencyContact, String dom, List<Member> members, List<Contact> contacts, Boolean visible) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.prayerGroup = prayerGroup;
            this.permanentAddress = permanentAddress;
            this.homeParish = homeParish;
            this.image = image;
            this.emergencyContact = emergencyContact;
            this.dom = dom;
            this.members = members;
            this.contacts = contacts;
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

        public List<Member> getMembers() {
            return members;
        }

        public void setMembers(List<Member> members) {
            this.members = members;
        }

        public List<Contact> getContacts() {
            return contacts;
        }

        public void setContacts(List<Contact> contacts) {
            this.contacts = contacts;
        }

        public Boolean getVisible() {
            return visible;
        }

        public void setVisible(Boolean visible) {
            this.visible = visible;
        }
    }

    public static class Member {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("relation")
        @Expose
        private String relation;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("bloodGroup")
        @Expose
        private String bloodGroup;
        @SerializedName("name")
        @Expose
        private String name;

        public Member(int id, String relation, String dob, String bloodGroup, String name) {
            this.id = id;
            this.relation = relation;
            this.dob = dob;
            this.bloodGroup = bloodGroup;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public class Response {

        @SerializedName("family")
        @Expose
        private List<Family> family = null;

        public List<Family> getFamily() {
            return family;
        }

        public void setFamily(List<Family> family) {
            this.family = family;
        }

    }

}