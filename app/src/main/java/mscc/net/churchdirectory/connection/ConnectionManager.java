package mscc.net.churchdirectory.connection;

import mscc.net.churchdirectory.inf.API;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dany on 26-01-2018.
 */

public class ConnectionManager {
    private static final ConnectionManager conManager = new ConnectionManager();
    private static API client;
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://msccsharjah.com/api/";

    public static ConnectionManager getInstance() {
        return conManager;
    }

    private ConnectionManager() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request baseRequest = chain.request();

            Request headerRequest = baseRequest.newBuilder()
                    .method(baseRequest.method(), baseRequest.body())
                    .build();

            return chain.proceed(headerRequest);
        });

        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build());

        retrofit = builder.build();

        client = retrofit.create(API.class);

    }

    public API getClient() {
        return client;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}



