package danysam.net.churchdirectory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dany on 26-01-2018.
 */

public class Families {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("permanentAddress")
    @Expose
    private String permanentAddress;
    @SerializedName("homeParish")
    @Expose
    private String homeParish;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("phoneResidence")
    @Expose
    private String phoneResidence;
    @SerializedName("phoneIndia")
    @Expose
    private String phoneIndia;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("dom")
    @Expose
    private String dom;
    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("prayerGroup")
    @Expose
    private Integer prayerGroup;
    @SerializedName("mobileSecondary")
    @Expose
    private String mobileSecondary;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhoneResidence() {
        if (phoneResidence == null) return "";
        return phoneResidence;
    }

    public void setPhoneResidence(String phoneResidence) {
        this.phoneResidence = phoneResidence;
    }

    public String getPhoneIndia() {
        if (phoneIndia == null) return "";
        return phoneIndia;
    }

    public void setPhoneIndia(String phoneIndia) {
        this.phoneIndia = phoneIndia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Integer getPrayerGroup() {
        return prayerGroup;
    }

    public void setPrayerGroup(Integer prayerGroup) {
        this.prayerGroup = prayerGroup;
    }

    public String getMobileSecondary() {
        if (mobileSecondary == null) return "";
        return mobileSecondary;
    }

    public void setMobileSecondary(String mobileSecondary) {
        this.mobileSecondary = mobileSecondary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
