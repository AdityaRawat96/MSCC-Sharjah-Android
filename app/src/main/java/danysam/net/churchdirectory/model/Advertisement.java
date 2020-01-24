package danysam.net.churchdirectory.model;

public class Advertisement {

    public String advertisementId;
    public String advertisementName;
    public String advertisementDescription;
    public String advertisementImageBanner;
    public String advertisementImageFull;
    public String advertisementWebsite;
    public String advertisementPhone;
    public String advertisementTimestamp;

    public Advertisement(String id, String name, String description, String banner, String full, String website, String phone, String timestamp){
        advertisementId = id;
        advertisementName = name;
        advertisementDescription = description;
        advertisementImageBanner = banner;
        advertisementImageFull = full;
        advertisementWebsite = website;
        advertisementPhone = phone;
        advertisementTimestamp = timestamp;
    }

    public Advertisement() {
    }

    public String getAdvertisementId() {
        return advertisementId;
    }

    public String getAdvertisementName() {
        return advertisementName;
    }

    public String getAdvertisementDescription() {
        return advertisementDescription;
    }

    public String getAdvertisementImageBanner() {
        return advertisementImageBanner;
    }

    public String getAdvertisementImageFull() {
        return advertisementImageFull;
    }

    public String getAdvertisementWebsite() {
        return advertisementWebsite;
    }

    public String getAdvertisementPhone() {
        return advertisementPhone;
    }

    public String getAdvertisementTimestamp() {
        return advertisementTimestamp;
    }

    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }

    public void setAdvertisementName(String advertisementName) {
        this.advertisementName = advertisementName;
    }

    public void setAdvertisementDescription(String advertisementDescription) {
        this.advertisementDescription = advertisementDescription;
    }

    public void setAdvertisementImageBanner(String advertisementImageBanner) {
        this.advertisementImageBanner = advertisementImageBanner;
    }

    public void setAdvertisementImageFull(String advertisementImageFull) {
        this.advertisementImageFull = advertisementImageFull;
    }

    public void setAdvertisementWebsite(String advertisementWebsite) {
        this.advertisementWebsite = advertisementWebsite;
    }

    public void setAdvertisementPhone(String advertisementPhone) {
        this.advertisementPhone = advertisementPhone;
    }

    public void setAdvertisementTimestamp(String advertisementTimestamp) {
        this.advertisementTimestamp = advertisementTimestamp;
    }
}
