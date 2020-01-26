package mscc.net.churchdirectory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dany on 26-01-2018.
 */

public class User {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("password")
    @Expose
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
