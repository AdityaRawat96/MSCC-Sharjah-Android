package danysam.net.churchdirectory.inf;

import java.util.List;

import danysam.net.churchdirectory.model.Articles;
import danysam.net.churchdirectory.model.Auth;
import danysam.net.churchdirectory.model.Directory;
import danysam.net.churchdirectory.model.Downloads;
import danysam.net.churchdirectory.model.ImageUpdate;
import danysam.net.churchdirectory.model.Members;
import danysam.net.churchdirectory.model.Notification;
import danysam.net.churchdirectory.model.PushToken;
import danysam.net.churchdirectory.model.Register;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Dany on 26-01-2018.
 */

public interface API {

    String USER_NAME = "user_name";
    String PASSWORD = "password";

    String HEADER_X_AUTH_ID = "X_AUTH_ID";
    String HEADER_X_AUTH_TOKEN = "X_AUTH_TOKEN";
    String HEADER_X_LAST_UPDATE = "X_LAST_UPDATE";

    String QUERY_NAME = "name";
    String QUERY_PHONE = "phone";
    String QUERY_EMAIL = "email";
    String QUERY_RESIDENCE = "residence";
    String QUERY_HOME_PARISH = "homeParish";
    String QUERY_DIOCESE = "diocese";
    String HEADER_PUSH_TOKEN = "X_PUSH_TOKEN_ANDROID";
    String PART_OTHER = "other";


    @GET("members")
    Observable<Response<List<Members>>> getMembers();

    @POST("auth")
    Observable<Response<Auth>> auth(@Query(USER_NAME) String userName, @Query(PASSWORD) String password);

    @GET("directory")
    Observable<Response<Directory>> getDirectory(@Header(HEADER_X_AUTH_ID) String id,
                                                 @Header(HEADER_X_AUTH_TOKEN) String token,
                                                 @Header(HEADER_X_LAST_UPDATE) String lastUpdate);

    @GET("notification")
    Observable<Response<Notification>> getNotifications();

    @GET("articles")
    Observable<Response<Articles>> getArticles();

    @GET("downloads")
    Observable<Response<Downloads>> getDownloads();

    @POST("register")
    Observable<Response<Register>> register(@Query(QUERY_NAME) String name,
                                            @Query(QUERY_PHONE) String phone,
                                            @Query(QUERY_EMAIL) String email,
                                            @Query(QUERY_RESIDENCE) String residence,
                                            @Query(QUERY_HOME_PARISH) String homeParish,
                                            @Query(QUERY_DIOCESE) String diocese);

    @POST("profile_update/")
    Observable<Response<Directory>> updateProfile(@Header(HEADER_X_AUTH_ID) String id,
                                                  @Header(HEADER_X_AUTH_TOKEN) String token,
                                                  @Body Directory.Family family);

    @POST("push_token")
    Observable<Response<PushToken>> sendToken(@Header(HEADER_X_AUTH_ID) String id,
                                              @Header(HEADER_X_AUTH_TOKEN) String token,
                                              @Header(HEADER_PUSH_TOKEN) String pushToken);

    @Multipart
    @POST("image_update/")
    Observable<Response<ImageUpdate>> updateImage(@Header(HEADER_X_AUTH_ID) String id,
                                                  @Header(HEADER_X_AUTH_TOKEN) String token,
                                                  @Part MultipartBody.Part image,
                                                  @Part(PART_OTHER) RequestBody other);
}
