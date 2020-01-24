package danysam.net.churchdirectory.model;

/**
 * Created by Dany on 16-01-2018.
 */

public class ContactUs {
    private String name;
    private String designation;
    private String phone;
    private int image;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactUs(String name, String designation, String phone, String email, int image) {

        this.name = name;
        this.designation = designation;
        this.phone = phone;
        this.image = image;
        this.email = email;
    }
}
