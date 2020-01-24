package danysam.net.churchdirectory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dany on 18-02-2018.
 */

public class Auth {

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

    public class Response {

        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("isMember")
        @Expose
        private Boolean isMember;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public Boolean getIsMember() {
            return isMember;
        }

        public void setIsMember(Boolean isMember) {
            this.isMember = isMember;
        }

    }
}
